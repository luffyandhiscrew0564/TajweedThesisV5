package V5Tajweed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;

import com.github.jsonldjava.shaded.com.google.common.collect.Lists;

import V5TajweedFactoryOnto.*;
import V5TajweedFactoryOnto.impl.*;
public class SurahName {
	private static OWLOntology ontology;
	private static  V5TajweedFactory tajweedV5Factory;
	private static String baseUrl= "http://www.tajweedontology.org/ontologies/rules#";
	//static Connection conn;
	//static Statement st;
	private static SWRLRuleEngine swrlRuleEngine;

	public static void main(String[] args) 
	{
		try {
			ConnectSQL.createConnectionwrite();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ReadFile();
		

	}
	public static void InitializeTajweedEngine() 
	{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File("FinalTajweedRulesWithoutRules.owl");

		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);
			tajweedV5Factory = new V5TajweedFactory(ontology);
			swrlRuleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
		} 
		catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void ReadFile() {
		String surahFileName = "Quran";
		String fileName = "C:\\Users\\Ramsha\\Desktop\\TxtFiles\\" + surahFileName + ".txt";
		File file = new File(fileName);
		String[][] ruleList = allRules();
		
		List<String> surahsToRead = new ArrayList();
		surahsToRead.add("3");
		
		List<String> versesToRead = new ArrayList();
		versesToRead.add("14");
		//versesToRead.add("3");
		List<String> versesToSkip = new ArrayList();
		//versesToSkip.add("1");
		//versesToSkip.add("3");
		
		try {
			for ( String[] ruleDefinition : ruleList) {
				for (String currentLineVar : FileUtils.readLines(file)) {
					String[] line = currentLineVar.split("\\|");
					String surahNo = line[0];
					String verseNo = line[1];
					String verse = line[2];
					if (!surahsToRead.isEmpty()) {
						if (!surahsToRead.contains(surahNo)) {
							//System.out.println("Skipping Surah " + surahNo);
							continue;
						}
						if (!versesToRead.isEmpty()) {
							if (!versesToRead.contains(verseNo)) {
							//	System.out.println("Skipping Verse " + verseNo);
								continue;
							}
						}
					}
					if (!versesToSkip.isEmpty()) {
						if(versesToSkip.contains(verseNo)) {
						//	System.out.println("Skipping Verse " + verseNo);
							continue;
						}
					}
					InitializeTajweedEngine();
					try {
						System.out.println("CREATE RULE " + ruleDefinition[1]);
						SWRLRule rule = swrlRuleEngine.createSWRLRule(ruleDefinition[1], ruleDefinition[2]);
					} catch (SWRLParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SWRLBuiltInException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String[] words = verse.split(" ");
					ParseStr(surahNo, verseNo, words) ;
					AdjustHarakats();
					
					System.out.println("Running Engine is running for --- "+ ruleDefinition[1]);
					RunEngine(); 
					System.out.println("Rules Inference Save for --- "+ ruleDefinition[1]);
					wordExceptions();
					saveont(surahNo,verseNo, ruleDefinition[0], ruleDefinition[1]);
					ConnectSQL.WriteToDatabase(surahNo, verseNo, ruleDefinition[0], ruleDefinition[1]);
				}

			}
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
		Harakat tanweenzaber =tajweedV5Factory.getHarakat(baseUrl+" ً");
		Letter Alif = tajweedV5Factory.getLetter(baseUrl+ "ا");
		Letter Yaa = tajweedV5Factory.getLetter(baseUrl+ "ى");
		String OccuranceFormat = "%sS%03d_V%03d_W%03d_LO%03d";
		int position = 0; //if starting with 1 change it to 0

		for (int wordNo = 0; wordNo < words.length; wordNo++) {
			String word = words[wordNo];
			char c [] = word.toCharArray();
			for (int i = 0; i < c.length; i++) {
				System.out.println("Character = " + c[i]);


				if (tajweedV5Factory.getLetter(baseUrl + c[i]) != null) {
					// was there a previous letter? store it in PreviousLOI
					if (LOI != null) { 
						PreviousLOI = LOI;
					}
					LI = tajweedV5Factory.getLetter(baseUrl+c[i]);
					String LetterOccurrenceID = String.format(
						OccuranceFormat,
						baseUrl,
						Integer.parseInt(surahNo), // surah
						Integer.parseInt(verseNo), // verse
						wordNo + 1, // word
						i + 1 // LO
					);
					LOI = tajweedV5Factory.createLetterOccurrence(LetterOccurrenceID);
					LOI.addInvolveLetter(LI);
					LOI.addInvolveSurahNo(Integer.parseInt(surahNo));
					//	LOI.addInvolveWordNo(Integer.parseInt(wordNo));
					LOI.addInvolveWord((word));
					//LOI.addHasPosition(position);
/*					int nextCharIdx = i + 1;
					int nextTwoCharIdx = i + 2;
					boolean haveSetPosition = false;
					if (!(nextCharIdx >= c.length) && tajweedV5Factory.getHarakat(baseUrl + c[nextCharIdx]) != null) {
						if (!(nextTwoCharIdx >= c.length) && tajweedV5Factory.getHarakat(baseUrl + c[nextTwoCharIdx]) != null) {
							System.out.println("Found next two chars after " + c[i] + " to be harakats");
							LOI.addHasLetterPosition(position + 2);
							haveSetPosition = true;
						}
					}
					if (!haveSetPosition) {
						LOI.addHasLetterPosition(position);
					}*/
					LOI.addHasLetterPosition(position);
					LOI.addInvolveVerseNo(Integer.parseInt(verseNo));
					if (PreviousLOI != null) {
						PreviousLOI.addFollowedBy(LOI); 
						LOI.addPrecededBy(PreviousLOI);
					}

				} else if(tajweedV5Factory.getHarakat(baseUrl+c[i])!=null) {
					Harakat HI = tajweedV5Factory.getHarakat(baseUrl+c[i]);
					LOI.addInvolveHarakat(HI);
					//LOI.addHasPosition(position);
					LOI.addHasHaraktPosition(position);
					int nextCharIdx = i + 1;
					int nextTwoCharIdx = i + 2;
					if (!(nextCharIdx >= c.length)) {
						if (c[i] == 'ً' ) {
							if (c[nextCharIdx] == 'ا' || c[nextCharIdx] =='ى' ) {
								i = i + 1;
								position++;
								System.out.println("Next character is alif or yaah");	
							} else if (c[nextCharIdx] == 'ۢ' && !(nextTwoCharIdx >= c.length) && c[nextTwoCharIdx] == 'ا') {
								i = i + 2;
								position++;
								System.out.println("Skipping small meem and alif");
							}
						}

					}
				} else {
					System.out.println(c[i] + " is not a letter or harakat (not in ontology)");
				}

				position++;
				System.out.println("LO = " + LOI);
			}
			position++;
		} // end-for
	} 

	public static void AdjustHarakats() {
		Harakat Sakina = tajweedV5Factory.getHarakat(baseUrl+'ْ');
		Tanween TanweenZair = tajweedV5Factory.getTanween(baseUrl+"ٍ");
		Tanween TanweenZaber = tajweedV5Factory.getTanween(baseUrl+ " ً");
		Tanween TanweenPaish = tajweedV5Factory.getTanween(baseUrl+"ٌ");
		Harakat Meem = tajweedV5Factory.getHarakat(baseUrl+'ۢ');
		Letter Noon = tajweedV5Factory.getLetter(baseUrl+"ن");
		Letter BigMeem = tajweedV5Factory.getLetter(baseUrl+ "م");
		Letter Ya = tajweedV5Factory.getLetter(baseUrl+ "ي");
		Letter waaw = tajweedV5Factory.getLetter(baseUrl+ "و");
		//	Harakat tanweenzaber =tajweedV5Factory.getHarakat(baseUrl+" ً");
		//	Letter Alif = tajweedV5Factory.getLetter(baseUrl+ "ا");
		//NoonSakinahAndTanween Iqlab = tajweedV5Factory.getNoonSakinahAndTanween(baseUrl + "Iqlab");
		Collection<? extends LetterOccurrence> letterOccurrences = tajweedV5Factory.getAllLetterOccurrenceInstances(); //Its an Array taking all the LO made from parsestr() getting LO from the ontology
		for (LetterOccurrence LO : letterOccurrences) {
			Collection involvedLetters = LO.getInvolveLetter();//getting all involve letter for LO

			if (involvedLetters.contains(Noon)) { //if the involve letter is noon
				Collection harakats = LO.getInvolveHarakat(); //Array for all harakat of LO
				if (harakats.isEmpty()) { // if No harakat
					//System.out.println("No harakats found");
					LO.addInvolveHarakat(Sakina); //add sakinah

				} else if (harakats.contains(Meem)) { 
					LO.removeInvolveHarakat(Meem);
					LO.addInvolveHarakat(Sakina);
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
	
	public static void wordExceptions() {
		String[] exceptions = {"ٱلدُّنْيَا","صِنْوَانٌ" , "قِنْوَانٌ" };
		Collection<? extends LetterOccurrence> letterOccurrences = tajweedV5Factory.getAllLetterOccurrenceInstances(); //Its an Array taking all the LO made from parsestr() getting LO from the ontology
		Collection<? extends RuleOccurrence> ruleOccurrences = tajweedV5Factory.getAllRuleOccurrenceInstances();
		for (LetterOccurrence LO : letterOccurrences) {
			Collection involvedWord = LO.getInvolveWord();
			if (involvedWord.contains(exceptions[0] )||involvedWord.contains(exceptions[1])||involvedWord.contains(exceptions[2])) {
				for (RuleOccurrence RO : ruleOccurrences) {
					Collection occurAt = RO.getOccurAt();
					if (occurAt.contains(LO)) {
						System.out.println("removing rule");
						RO.delete();
						//System.exit(0);	
					}
				}
			}
		}
	}


	public static void RunEngine() {

		swrlRuleEngine.infer();
		System.out.println("All rules are infered");
	}

	public static String[][] allRules() {


		String[] rule1 = new String[]{"R001","IkhfaRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Ikhfa) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule2 = new String[]{"R002","IqlabRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IqlabLetter(?L) ^ hasLetterPosition(?LO, ?P) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Iqlab) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule3 = new String[]{"R003","IdghamShafawiRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamShafawiLetter(?L) ^ hasLetterPosition(?LO, ?P) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamShafawi) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule4 = new String[]{"R004","TanweenIqlabRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IqlabLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Iqlab) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule5 = new String[]{"R005","TanweenIdghamWithGhunnahRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghamWithGhunnah) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule6 = new String[]{"R006","TanweenIdghamWithoutGhunnahRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithoutGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamWithoutGhunnah) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule7 = new String[]{"R007","TanweenIkhfaRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Ikhfa) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule8 = new String[]{"R008","IdghamWithGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghamWithGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule9 = new String[]{"R009","IdghamwithoutGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithoutGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamWithoutGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule10 = new String[]{"R010","MostCompleteGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ّ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, MostCompleteGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule11 = new String[]{"R011","MostCompleteGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ّ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, MostCompleteGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule12 = new String[]{"R012","Qalqalah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?L) ^ QalqalahLetter(?L) ^ involveHarakat(?LO, ْ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Qalqalah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule13 = new String[]{"R013","IkhfaShafawiRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaShafawiLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasLetterPosition(?R, ?P) ^ hasRuleType(?R, IkhfaShafawi) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};

		//String[][] rules  = {rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12, rule13};
		String[][] rules  = {rule8};
		return rules;
	}

	public static void saveont(String surahNo, String verseNo, String ruleDefinition, String rule)

	{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		File fileformated = new File("S"+surahNo + "_" +"V"+ verseNo + "_" +ruleDefinition+ "_"+ rule + ".owl");
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
}
