package V5Tajweed;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import V5TajweedFactoryOnto.Harakat;
import V5TajweedFactoryOnto.Letter;
import V5TajweedFactoryOnto.LetterOccurrence;
import V5TajweedFactoryOnto.RuleOccurrence;
import V5TajweedFactoryOnto.Tanween;
import V5TajweedFactoryOnto.V5TajweedFactory;
public class ConnectSQL {

	static Connection conn;
	static Statement st;
	private static String baseUrl= "http://www.tajweedontology.org/ontologies/rules#";
	

	/*	public static void createConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		String  url = "jdbc:mysql://localhost:3306/Quran?characterEncoding=utf8"; 
		conn = DriverManager.getConnection(url,"root", ""); 
		st = conn.createStatement(); 
		System.out.println("connection created");
	}
*/	
	public static void createConnectionwrite() throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		String  url = "jdbc:mysql://localhost:3306/testing?characterEncoding=utf8"; 
		conn = DriverManager.getConnection(url,"root", ""); 
		st = conn.createStatement(); 
		System.out.println("connection created");
	}
	public static void insertdata(Integer SurahNo, Integer VerseNo, String RuleType, Integer LetterPosition)
	{
		Pattern p = Pattern.compile("\\b(Iqlab|Izhar|IdghaamWithoutGhunnah|IdghamWithGhunnah|Ikhfa|Qalqalah|IdghaamShafawi|IkhfaShafawi|IzharShafawi|Hamzatulwasal|MostCompleteGhunnah|Ghunnah|NoonSakinahAndTanween|MeemSakinah)\\b");
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
		System.out.println("Will execute: " + Statement);
		try {
			System.out.println(st);
			st.executeUpdate(Statement);
		} catch (Exception e) { 
			System.err.println("Got an exception! " + e); 
			System.err.println(e.getMessage()); 
			System.exit(0);
		}
	}
	public static void WriteToDatabase(String surahNo, String verseNo,String ruleDefinition , String rule ) {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File("S"+surahNo + "_" +"V"+ verseNo + "_" +ruleDefinition+ "_"+ rule + ".owl");
		System.out.println("Reading file to Write to db");

		try {
			OWLOntology singleOntology = manager.loadOntologyFromOntologyDocument(file);
			V5TajweedFactory tajweedFactory = new V5TajweedFactory(singleOntology);

			System.out.println("File  found");
			Collection ruleocc = tajweedFactory.getAllRuleOccurrenceInstances();
			Iterator itr=ruleocc.iterator();
			Letter BigMeem = tajweedFactory.getLetter(baseUrl+ "م");
			Harakat Shaad = tajweedFactory.getHarakat(baseUrl+'ّ');

			while ( itr.hasNext() ) {
				RuleOccurrence ro = (RuleOccurrence) itr.next();

				System.out.println(" Printing all the RO"+ ro.toString());
				System.out.println("Instances of RO"+ ro.getOwlIndividual().toStringID());
				Integer letterPosition = ro.getHasLetterPosition().iterator().next();	
				Integer surahNo1 = ro.getInvolveSurahNo().iterator().next();
				Integer verseNo1 = ro.getInvolveVerseNo().iterator().next();
				
				Collection<? extends WrappedIndividual> occurAts = ro.getOccurAt();
				for (WrappedIndividual OA : occurAts) {
					LetterOccurrence occurAt = tajweedFactory.as(OA, LetterOccurrence.class);
					Collection<? extends WrappedIndividual> harakats = occurAt.getInvolveHarakat();
					Collection<? extends WrappedIndividual> letters = occurAt.getInvolveLetter();
					boolean hasTanween = false;
					for (WrappedIndividual harakat : harakats) {
						if (tajweedFactory.canAs(harakat, Tanween.class)) {
							hasTanween = true;
							break;
						}
					}
					if (hasTanween) {
						Collection<? extends Integer> harakatPositions = occurAt.getHasHaraktPosition();
						Integer harakatPosition = 0;
						for (Integer pos : harakatPositions) {
							if (pos > harakatPosition) {
								harakatPosition = pos;
							}
						}
						letterPosition = harakatPosition;
					}
					if (letters.contains(BigMeem) && harakats.contains(Shaad)) {
						Collection<? extends Integer> harakatPositions = occurAt.getHasHaraktPosition();
						Integer harakatPosition = 0;
						for (Integer pos : harakatPositions) {
							if (pos > harakatPosition) {
								harakatPosition = pos;
							}
						}
						letterPosition = harakatPosition - 1;
					}
				}
				
				ConnectSQL.insertdata(surahNo1, verseNo1, ro.toString(), letterPosition);
			}

			System.out.println("file read");
		}
		catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
/*	public static void readData() {

		String Statement = "SELECT * From quran_text WHERE sura = 1 AND aya = 2";
		try {
			ResultSet res = st.executeQuery(Statement);
			System.out.println("Data: " + Statement);
			while (res.next()) {
				int sura = res.getInt("sura");
				int  verses = res.getInt("aya");
				String  text = res.getString("text");
				System.out.println("surah = "+ sura + "Text =" + text);

			}
			System.out.println("Data is Displayed");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Got an exception! " + e); 
			System.err.println(e.getMessage()); 
			System.exit(0);
		} 

	}
	*/
}

