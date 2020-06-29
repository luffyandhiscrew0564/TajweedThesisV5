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
	public class Swrl {
		private static OWLOntologyManager manager;
		private static OWLOntology ontology;
		private static  V5TajweedFactory tajweedV5Factory;
		private static String baseUrl= "http://www.tajweedontology.org/ontologies/rules#";
		static Connection conn;
		static Statement st;
		private static SWRLRuleEngine swrlRuleEngine;

		public static void main(String[] args) 
		{
			// TODO Auto-generated method stub
			//InitializeTajweedEngine();
			
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
			String surahFileName = "SurahBaqra102";
			String fileName = "C:\\Users\\Ramsha\\Desktop\\TxtFiles\\" + surahFileName + ".txt";
			File file = new File(fileName);
			String[][] ruleList = allRules();
			
			try {
				for ( String[] ruleDefinition : ruleList) {
					InitializeTajweedEngine();
					try {
						SWRLRule rule = swrlRuleEngine.createSWRLRule(ruleDefinition[0], ruleDefinition[1]);
					} catch (SWRLParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SWRLBuiltInException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (String currentLineVar : FileUtils.readLines(file)) {
						//InitializeTajweedEngine();
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
						swrlRuleEngine.infer(); 
						System.out.println("Running Save --- ");
					
				
						
					}
					//swrlRuleEngine.deleteSWRLRule(ruleDefinition[0]);
					saveont(surahFileName);
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

		//String OccuranceFormat = "%sLO%03d_V%03d_W%03d_S%03d";
		String OccuranceFormat = "%sS%03d_V%03d_W%03d_LO%03d";
		int position = 0; //if starting with 1 change it to 0

		for (int wordNo = 0; wordNo < words.length; wordNo++) {
			String word = words[wordNo];
			char c [] = word.toCharArray();
			for (int i = 0; i < c.length; i++) {
				/*
				 * if (c[i] == 'ـ') { System.out.println("Empty char -- skipping"); continue;
				 * 
				 * 
				 * }
				 */

				System.out.println("Character = " + c[i]);


				if (tajweedV5Factory.getLetter(baseUrl + c[i]) != null) {
					// was there a previous letter? store it in PreviousLOI
					if (LOI != null) { 
						PreviousLOI = LOI;
					}

					LI = tajweedV5Factory.getLetter(baseUrl+c[i]);
					String LetterOccurrenceID = String.format(OccuranceFormat, baseUrl,Integer.parseInt(surahNo),Integer.parseInt(verseNo),wordNo + 1, i + 1 );
					LOI = tajweedV5Factory.createLetterOccurrence(LetterOccurrenceID);
					LOI.addInvolveLetter(LI);
					LOI.addInvolveSurahNo(Integer.parseInt(surahNo));
					//	LOI.addInvolveWordNo(Integer.parseInt(wordNo));
					LOI.addInvolveWord((word));
					//LOI.addHasPosition(position);
					LOI.addHasLetterPosition(position);


					LOI.addInvolveVerseNo(Integer.parseInt(verseNo));
					if (PreviousLOI != null) {
						PreviousLOI.addFollowedBy(LOI); 
						LOI.addPrecededBy(PreviousLOI);
					}

					//continue; //next index pr jao
				}

				if(tajweedV5Factory.getHarakat(baseUrl+c[i])!=null) {
					Harakat HI = tajweedV5Factory.getHarakat(baseUrl+c[i]);
					LOI.addInvolveHarakat(HI);
					//LOI.addHasPosition(position);
					LOI.addHasHaraktPosition(position);
					int nextCharIdx = i + 1;
					int nextTwoCharIdx = i + 2;
					if (!(nextCharIdx >= c.length)) {
						if (c[i] == 'ً' ) {
							if (c[nextCharIdx] == 'ا' ) {
								i = i + 1;
								System.out.println("Next character is alif");	
							} else if (c[nextCharIdx] == 'ۢ' && !(nextTwoCharIdx >= c.length) && c[nextTwoCharIdx] == 'ا') {
								i = i + 2;
								System.out.println("Skipping small meem and alif");
							}
						}

					}
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

		
		/* try {
			 
			SWRLRule rule = swrlRuleEngine.createSWRLRule("IkhfaRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Ikhfa) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule1 = swrlRuleEngine.createSWRLRule("IqlabRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IqlabLetter(?L) ^ hasLetterPosition(?LO, ?P) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Iqlab) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule2 = swrlRuleEngine.createSWRLRule("IdghamShafawiRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamShafawiLetter(?L) ^ hasLetterPosition(?LO, ?P) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamShafawi) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule3 = swrlRuleEngine.createSWRLRule("TanweenIqlabRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IqlabLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Iqlab) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)");
			SWRLRule rule4 = swrlRuleEngine.createSWRLRule("TanweenIdghamWithGhunnahRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghamWithGhunnah) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)");
			SWRLRule rule5 = swrlRuleEngine.createSWRLRule("TanweenIdghamWithoutGhunnahRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithoutGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamWithoutGhunnah) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)");
			SWRLRule rule6 = swrlRuleEngine.createSWRLRule("TanweenIkhfaRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Ikhfa) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)");
			SWRLRule rule7 = swrlRuleEngine.createSWRLRule("IdghamWithGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghamWithGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule8 = swrlRuleEngine.createSWRLRule("IdghamwithoutGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithoutGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamWithoutGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule9 = swrlRuleEngine.createSWRLRule("MostCompleteGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ّ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, MostCompleteGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule10 = swrlRuleEngine.createSWRLRule("MostCompleteGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ّ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, MostCompleteGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule11 = swrlRuleEngine.createSWRLRule("Qalqalah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?L) ^ QalqalahLetter(?L) ^ involveHarakat(?LO, ْ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Qalqalah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			SWRLRule rule12 = swrlRuleEngine.createSWRLRule("IkhfaShafawiRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaShafawiLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasLetterPosition(?R, ?P) ^ hasRuleType(?R, IkhfaShafawi) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)");
			
		} catch (SWRLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SWRLBuiltInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		swrlRuleEngine.infer();   
	}

	public static String[][] allRules() {
		
		String[] rule1 = new String[]{"IkhfaRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Ikhfa) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule2 = new String[]{"IqlabRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IqlabLetter(?L) ^ hasLetterPosition(?LO, ?P) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Iqlab) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule3 = new String[]{"IdghamShafawiRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamShafawiLetter(?L) ^ hasLetterPosition(?LO, ?P) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamShafawi) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule4 = new String[]{"TanweenIqlabRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IqlabLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Iqlab) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule5 = new String[]{"TanweenIdghamWithGhunnahRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghamWithGhunnah) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule6 = new String[]{"TanweenIdghamWithoutGhunnahRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithoutGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamWithoutGhunnah) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule7 = new String[]{"TanweenIkhfaRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?p) ^ Letter(?p) ^ involveHarakat(?LO, ?T) ^ Tanween(?T) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Ikhfa) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V) ^ hasLetterPosition(?R, ?P)"};
		String[] rule8 = new String[]{"IdghamWithGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghamWithGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule9 = new String[]{"IdghamwithoutGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IdghaamWithoutGhunnahLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, IdghaamWithoutGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule10 = new String[]{"MostCompleteGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ّ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, MostCompleteGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule11 = new String[]{"MostCompleteGhunnah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ن) ^ involveHarakat(?LO, ّ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, MostCompleteGhunnah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule12 = new String[]{"Qalqalah", "LetterOccurrence(?LO) ^ involveLetter(?LO, ?L) ^ QalqalahLetter(?L) ^ involveHarakat(?LO, ْ) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasRuleType(?R, Qalqalah) ^ hasLetterPosition(?R, ?P) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		String[] rule13 = new String[]{"IkhfaShafawiRule", "LetterOccurrence(?LO) ^ involveLetter(?LO, م) ^ involveHarakat(?LO, ْ) ^ followedBy(?LO, ?LOF) ^ LetterOccurrence(?LOF) ^ involveLetter(?LOF, ?L) ^ IkhfaShafawiLetter(?L) ^ involveSurahNo(?LO, ?S) ^ involveVerseNo(?LO, ?V) ^ hasLetterPosition(?LO, ?P) ^ swrlx:makeOWLThing(?R, ?LO, ?LOF) -> RuleOccurrence(?R) ^ occurAt(?R, ?LO) ^ hasLetterPosition(?R, ?P) ^ hasRuleType(?R, IkhfaShafawi) ^ involveSurahNo(?R, ?S) ^ involveVerseNo(?R, ?V)"};
		
		String[][] rules  = {rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10, rule11, rule12, rule13};
		return rules;
	}
	
	public static void saveont(String verseNo)

	{
		manager = OWLManager.createOWLOntologyManager();
		File fileformated = new File(verseNo + ".owl");
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
