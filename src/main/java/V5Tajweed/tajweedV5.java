package V5Tajweed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.drools.core.rule.Collect;
import org.protege.owl.codegeneration.WrappedIndividual;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.factory.SWRLAPIFactory;

import V5TajweedFactoryOnto.*;
import V5TajweedFactoryOnto.impl.*;

public class tajweedV5 {
	private static OWLOntologyManager manager;
	private static OWLOntology ontology;
	private static  V5TajweedFactory tajweedV5Factory;
	private static String baseUrl= "http://www.tajweedontology.org/ontologies/rules#";
	static Connection conn;
	static Statement st;

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		InitializeTajweedEngine();
		try {
			createConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadFile();
		WriteToDatabase();
	}
	public static void InitializeTajweedEngine() 
	{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File("FinalTajweedRules.owl");

		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);
			tajweedV5Factory = new V5TajweedFactory(ontology);	

		} 
		catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void ReadFile() {
		String fileName = "C:\\Users\\Ramsha\\Desktop\\TxtFiles\\SurahQadr.txt";
		File file = new File(fileName);

		int VerseNo = 4 - 1; // if zero remove minus 1
		try {
			//BufferedReader reader = new BufferedReader(new FileReader(file));
			String currentLineVar = FileUtils.readLines(file).get(VerseNo);
			//while ((currentLineVar = reader.readLine())!=null) {
			if (currentLineVar.isEmpty()) {
				//	continue;
			}
			String[] line = currentLineVar.split("\\|");
			String surahNo = line[0];
			String verseNo = line[1];
			String verse = line[2];
			String[] words = verse.split(" ");
			ParseStr(surahNo, verseNo, words) ;
			AdjustHarakats();
			System.out.println("Running Engine --- ");
			RunEngine();
			System.out.println("Running Save --- ");
			saveont(surahNo,verseNo);

			//}
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void ParseStr(String surahNo, String verseNo, String[] words) {
		Letter LI = null;
		LetterOccurrence LOI= null;
		LetterOccurrence PreviousLOI = null;

		//String OccuranceFormat = "%sLO%03d_V%03d_W%03d_S%03d";
		String OccuranceFormat = "%sS%03d_V%03d_W%03d_LO%03d";
		int position = 0; //if starting with 1 change it to 0

		for (int wordNo = 0; wordNo < words.length; wordNo++) {
			String word = words[wordNo];
			char c [] = word.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == 'ـ') {
					System.out.println("Empty char -- skipping");
					continue;
				}
				System.out.println("Character = " + c[i]);


				if (tajweedV5Factory.getLetter(baseUrl + c[i]) != null) {
					// was there a previous letter? store it in PreviousLOI
					if (LOI != null) { // read hW yah to starting --if se bahir nikl aoo 
						PreviousLOI = LOI;
					}

					LI = tajweedV5Factory.getLetter(baseUrl+c[i]);
					String LetterOccurrenceID = String.format(OccuranceFormat, baseUrl,Integer.parseInt(surahNo),Integer.parseInt(verseNo),wordNo + 1, i + 1 );
					LOI = tajweedV5Factory.createLetterOccurrence(LetterOccurrenceID);
					LOI.addInvolveLetter(LI);//is lO mein add hogaya HW
					LOI.addInvolveSurahNo(Integer.parseInt(surahNo));
					//	LOI.addInvolveWordNo(Integer.parseInt(wordNo));
					LOI.addInvolveWord((word));
					//LOI.addHasPosition(position);
					LOI.addHasLetterPosition(position);


					LOI.addInvolveVerseNo(Integer.parseInt(verseNo));
					if (PreviousLOI != null) {
						PreviousLOI.addFollowedBy(LOI); //next waly pr foolwed kray ga kun k hum ne oper jo cond laggi ha woh ture hogi yahni null nhi hoga..
						LOI.addPrecededBy(PreviousLOI);
					}

					//continue; //next index pr jao
				}

				if(tajweedV5Factory.getHarakat(baseUrl+c[i])!=null) {
					Harakat HI = tajweedV5Factory.getHarakat(baseUrl+c[i]);
					LOI.addInvolveHarakat(HI);
					//LOI.addHasPosition(position);
					LOI.addHasHaraktPosition(position);
				}

				position++;
				System.out.println("LO = " + LOI);
			}
			position++;
		} // end-for


	} 

	public static void AdjustHarakats() {
		Harakat Sakina = tajweedV5Factory.getHarakat(baseUrl+'ْ');
		Harakat Meem = tajweedV5Factory.getHarakat(baseUrl+'ۢ');
		Letter Noon = tajweedV5Factory.getLetter(baseUrl+"ن");
		Letter BigMeem = tajweedV5Factory.getLetter(baseUrl+ "م");
		NoonSakinahAndTanween Iqlab = tajweedV5Factory.getNoonSakinahAndTanween(baseUrl + "Iqlab");
		Collection<? extends LetterOccurrence> letterOccurrences = tajweedV5Factory.getAllLetterOccurrenceInstances(); //Its an Array taking all the LO made from parsestr() getting LO from the ontology
		for (LetterOccurrence LO : letterOccurrences) {
			Collection involvedLetters = LO.getInvolveLetter(); //getting all involve letter for LO
			if (involvedLetters.contains(Noon)) { //if the involve letter is noon
				Collection harakats = LO.getInvolveHarakat(); //Array for all harakat of LO
				if (harakats.isEmpty()) { // if No harakat
					//System.out.println("No harakats found");
					LO.addInvolveHarakat(Sakina); //add sakinah

				} else if (harakats.contains(Meem)) { 
					LO.removeInvolveHarakat(Meem);
					LO.addInvolveHarakat(Sakina);
					//RuleOccurrence iqlabRule = tajweedV5Factory.createRuleOccurrence(baseUrl + "Iqlab");
					//iqlabRule.addHasRuleType(Iqlab);
					//iqlabRule.addOccurAt(LO);
				}
			}
			if (involvedLetters.contains(BigMeem)) {
				
				Collection harakats =LO.getInvolveHarakat();
				if (harakats.isEmpty()) {
					LO.addInvolveHarakat(Sakina);
				}
				
			}
		
		}
	}


	public static void RunEngine() {

		SWRLRuleEngine swrlRuleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology); 
		swrlRuleEngine.infer();   
	}

	public static void saveont(String surahNo, String verseNo)

	{
		manager = OWLManager.createOWLOntologyManager();
		File fileformated = new File(surahNo +"_" +verseNo + ".owl");
		try {
			ontology.saveOntology(new FunctionalSyntaxDocumentFormat(), new FileOutputStream(fileformated));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createConnection() throws SQLException, ClassNotFoundException
	{

		Class.forName("com.mysql.jdbc.Driver");
		String  url = "jdbc:mysql://localhost:3306/tajweed?characterEncoding=utf8"; 
		conn = DriverManager.getConnection(url,"root", ""); 
		st = conn.createStatement(); 
		System.out.println("connection created");
	}

	public static void WriteToDatabase() {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File("97_4.owl");

		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);
			tajweedV5Factory = new V5TajweedFactory(ontology);
		
			Collection ruleocc = tajweedV5Factory.getAllRuleOccurrenceInstances();
			Iterator itr=ruleocc.iterator();

			while ( itr.hasNext() ) {
				RuleOccurrence ro = (RuleOccurrence) itr.next();
				
				System.out.println(" Printing all the RO"+ ro.toString());
				System.out.println("Instances of RO"+ ro.getOwlIndividual().toStringID());
			
				//Collection letterOcc = ro.getOccurAt();
				Integer letterPosition = ro.getHasLetterPosition().iterator().next();	
				Integer surahNo = ro.getInvolveSurahNo().iterator().next();
				Integer verseNo = ro.getInvolveVerseNo().iterator().next();
				insertdata(surahNo, verseNo, ro.toString(), letterPosition);
				//Collection ruleTypes = ro.getHasRuleType();
				//Iterator ruleItr = ruleTypes.iterator();
				//while (ruleItr.hasNext()) {
				//	Object ruleType = ruleItr.next();
				//	System.out.println(ruleType);
				//	insertdata(surahNo, verseNo, ruleType.toString(), letterPosition);
			//	}
			}

			System.out.println("file read");
		}
		catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	// No able to find way to use RO.gethasRuleType() to find the rule. So using object.toString as work around  
	public static void insertdata(Integer SurahNo, Integer VerseNo, String RuleType, Integer LetterPosition) {
		Pattern p = Pattern.compile("(Iqlab|Izhar|IdghaamWithoutGhunnah|IdghamWithGhunnah|Ikhfa|Qalqalah|IdghaamShafawi|IkhfaShafawi|IzharShafawi|Hamzatulwasal|MostCompleteGhunnah|Ghunnah|NoonSakinahAndTanween|MeemSakinah)");
		Matcher m = p.matcher(RuleType);
		String Rule = "";
		if (m.find()) {
			Rule = m.group(1);
		} else {
			System.err.println("Rule not found in string! RuleType =  " + RuleType);
			return;
		}
		String Statement = "INSERT INTO tajweedannotations (rule, indexstart, verse, chapter) " +
				"VALUES ('" +
				Rule + "'," +
				LetterPosition + "," +
				VerseNo + "," +
				SurahNo + ")";
		//System.out.println("Will execute: " + Statement);
		try {
			st.executeUpdate(Statement);
		} catch (Exception e) { 
			System.err.println("Got an exception! "); 
			System.err.println(e.getMessage()); 
			System.exit(0);
		}

	}
	
	
}
