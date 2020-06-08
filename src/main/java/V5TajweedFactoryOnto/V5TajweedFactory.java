package V5TajweedFactoryOnto;

import V5TajweedFactoryOnto.impl.*;


import java.util.Collection;

import org.protege.owl.codegeneration.CodeGenerationFactory;
import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.FactoryHelper;
import org.protege.owl.codegeneration.impl.ProtegeJavaMapping;
import org.protege.owl.codegeneration.inference.CodeGenerationInference;
import org.protege.owl.codegeneration.inference.SimpleInference;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * A class that serves as the entry point to the generated code providing access
 * to existing individuals in the ontology and the ability to create new individuals in the ontology.<p>
 * 
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: V5TajweedFactory<br>
 * @version generated on Thu Jun 04 21:14:55 PKT 2020 by Ramsha
 */
public class V5TajweedFactory implements CodeGenerationFactory {
    private OWLOntology ontology;
    private ProtegeJavaMapping javaMapping = new ProtegeJavaMapping();
    private FactoryHelper delegate;
    private CodeGenerationInference inference;

    public V5TajweedFactory(OWLOntology ontology) {
	    this(ontology, new SimpleInference(ontology));
    }
    
    public V5TajweedFactory(OWLOntology ontology, CodeGenerationInference inference) {
        this.ontology = ontology;
        this.inference = inference;
        javaMapping.initialize(ontology, inference);
        delegate = new FactoryHelper(ontology, inference);
    }

    public OWLOntology getOwlOntology() {
        return ontology;
    }
    
    public void saveOwlOntology() throws OWLOntologyStorageException {
        ontology.getOWLOntologyManager().saveOntology(ontology);
    }
    
    public void flushOwlReasoner() {
        delegate.flushOwlReasoner();
    }
    
    public boolean canAs(WrappedIndividual resource, Class<? extends WrappedIndividual> javaInterface) {
    	return javaMapping.canAs(resource, javaInterface);
    }
    
    public  <X extends WrappedIndividual> X as(WrappedIndividual resource, Class<? extends X> javaInterface) {
    	return javaMapping.as(resource, javaInterface);
    }
    
    public Class<?> getJavaInterfaceFromOwlClass(OWLClass cls) {
        return javaMapping.getJavaInterfaceFromOwlClass(cls);
    }
    
    public OWLClass getOwlClassFromJavaInterface(Class<?> javaInterface) {
	    return javaMapping.getOwlClassFromJavaInterface(javaInterface);
    }
    
    public CodeGenerationInference getInference() {
        return inference;
    }

    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Ghunnah
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Ghunnah", Ghunnah.class, DefaultGhunnah.class);
    }

    /**
     * Creates an instance of type Ghunnah.  Modifies the underlying ontology.
     */
    public Ghunnah createGhunnah(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_GHUNNAH, DefaultGhunnah.class);
    }

    /**
     * Gets an instance of type Ghunnah with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Ghunnah getGhunnah(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_GHUNNAH, DefaultGhunnah.class);
    }

    /**
     * Gets all instances of Ghunnah from the ontology.
     */
    public Collection<? extends Ghunnah> getAllGhunnahInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_GHUNNAH, DefaultGhunnah.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Hamzatulwasal
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Hamzatulwasal", Hamzatulwasal.class, DefaultHamzatulwasal.class);
    }

    /**
     * Creates an instance of type Hamzatulwasal.  Modifies the underlying ontology.
     */
    public Hamzatulwasal createHamzatulwasal(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_HAMZATULWASAL, DefaultHamzatulwasal.class);
    }

    /**
     * Gets an instance of type Hamzatulwasal with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Hamzatulwasal getHamzatulwasal(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_HAMZATULWASAL, DefaultHamzatulwasal.class);
    }

    /**
     * Gets all instances of Hamzatulwasal from the ontology.
     */
    public Collection<? extends Hamzatulwasal> getAllHamzatulwasalInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_HAMZATULWASAL, DefaultHamzatulwasal.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Harakat
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Harakat", Harakat.class, DefaultHarakat.class);
    }

    /**
     * Creates an instance of type Harakat.  Modifies the underlying ontology.
     */
    public Harakat createHarakat(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_HARAKAT, DefaultHarakat.class);
    }

    /**
     * Gets an instance of type Harakat with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Harakat getHarakat(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_HARAKAT, DefaultHarakat.class);
    }

    /**
     * Gets all instances of Harakat from the ontology.
     */
    public Collection<? extends Harakat> getAllHarakatInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_HARAKAT, DefaultHarakat.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IdghaamShafawiLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IdghaamShafawiLetter", IdghaamShafawiLetter.class, DefaultIdghaamShafawiLetter.class);
    }

    /**
     * Creates an instance of type IdghaamShafawiLetter.  Modifies the underlying ontology.
     */
    public IdghaamShafawiLetter createIdghaamShafawiLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IDGHAAMSHAFAWILETTER, DefaultIdghaamShafawiLetter.class);
    }

    /**
     * Gets an instance of type IdghaamShafawiLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IdghaamShafawiLetter getIdghaamShafawiLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IDGHAAMSHAFAWILETTER, DefaultIdghaamShafawiLetter.class);
    }

    /**
     * Gets all instances of IdghaamShafawiLetter from the ontology.
     */
    public Collection<? extends IdghaamShafawiLetter> getAllIdghaamShafawiLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IDGHAAMSHAFAWILETTER, DefaultIdghaamShafawiLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IdghaamWithGhunnahLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IdghaamWithGhunnahLetter", IdghaamWithGhunnahLetter.class, DefaultIdghaamWithGhunnahLetter.class);
    }

    /**
     * Creates an instance of type IdghaamWithGhunnahLetter.  Modifies the underlying ontology.
     */
    public IdghaamWithGhunnahLetter createIdghaamWithGhunnahLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IDGHAAMWITHGHUNNAHLETTER, DefaultIdghaamWithGhunnahLetter.class);
    }

    /**
     * Gets an instance of type IdghaamWithGhunnahLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IdghaamWithGhunnahLetter getIdghaamWithGhunnahLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IDGHAAMWITHGHUNNAHLETTER, DefaultIdghaamWithGhunnahLetter.class);
    }

    /**
     * Gets all instances of IdghaamWithGhunnahLetter from the ontology.
     */
    public Collection<? extends IdghaamWithGhunnahLetter> getAllIdghaamWithGhunnahLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IDGHAAMWITHGHUNNAHLETTER, DefaultIdghaamWithGhunnahLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IdghaamWithoutGhunnahLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IdghaamWithoutGhunnahLetter", IdghaamWithoutGhunnahLetter.class, DefaultIdghaamWithoutGhunnahLetter.class);
    }

    /**
     * Creates an instance of type IdghaamWithoutGhunnahLetter.  Modifies the underlying ontology.
     */
    public IdghaamWithoutGhunnahLetter createIdghaamWithoutGhunnahLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IDGHAAMWITHOUTGHUNNAHLETTER, DefaultIdghaamWithoutGhunnahLetter.class);
    }

    /**
     * Gets an instance of type IdghaamWithoutGhunnahLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IdghaamWithoutGhunnahLetter getIdghaamWithoutGhunnahLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IDGHAAMWITHOUTGHUNNAHLETTER, DefaultIdghaamWithoutGhunnahLetter.class);
    }

    /**
     * Gets all instances of IdghaamWithoutGhunnahLetter from the ontology.
     */
    public Collection<? extends IdghaamWithoutGhunnahLetter> getAllIdghaamWithoutGhunnahLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IDGHAAMWITHOUTGHUNNAHLETTER, DefaultIdghaamWithoutGhunnahLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IkhfaLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IkhfaLetter", IkhfaLetter.class, DefaultIkhfaLetter.class);
    }

    /**
     * Creates an instance of type IkhfaLetter.  Modifies the underlying ontology.
     */
    public IkhfaLetter createIkhfaLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IKHFALETTER, DefaultIkhfaLetter.class);
    }

    /**
     * Gets an instance of type IkhfaLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IkhfaLetter getIkhfaLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IKHFALETTER, DefaultIkhfaLetter.class);
    }

    /**
     * Gets all instances of IkhfaLetter from the ontology.
     */
    public Collection<? extends IkhfaLetter> getAllIkhfaLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IKHFALETTER, DefaultIkhfaLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IkhfaShafawiLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IkhfaShafawiLetter", IkhfaShafawiLetter.class, DefaultIkhfaShafawiLetter.class);
    }

    /**
     * Creates an instance of type IkhfaShafawiLetter.  Modifies the underlying ontology.
     */
    public IkhfaShafawiLetter createIkhfaShafawiLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IKHFASHAFAWILETTER, DefaultIkhfaShafawiLetter.class);
    }

    /**
     * Gets an instance of type IkhfaShafawiLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IkhfaShafawiLetter getIkhfaShafawiLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IKHFASHAFAWILETTER, DefaultIkhfaShafawiLetter.class);
    }

    /**
     * Gets all instances of IkhfaShafawiLetter from the ontology.
     */
    public Collection<? extends IkhfaShafawiLetter> getAllIkhfaShafawiLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IKHFASHAFAWILETTER, DefaultIkhfaShafawiLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IqlabLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IqlabLetter", IqlabLetter.class, DefaultIqlabLetter.class);
    }

    /**
     * Creates an instance of type IqlabLetter.  Modifies the underlying ontology.
     */
    public IqlabLetter createIqlabLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IQLABLETTER, DefaultIqlabLetter.class);
    }

    /**
     * Gets an instance of type IqlabLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IqlabLetter getIqlabLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IQLABLETTER, DefaultIqlabLetter.class);
    }

    /**
     * Gets all instances of IqlabLetter from the ontology.
     */
    public Collection<? extends IqlabLetter> getAllIqlabLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IQLABLETTER, DefaultIqlabLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Item
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Item", Item.class, DefaultItem.class);
    }

    /**
     * Creates an instance of type Item.  Modifies the underlying ontology.
     */
    public Item createItem(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_ITEM, DefaultItem.class);
    }

    /**
     * Gets an instance of type Item with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Item getItem(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_ITEM, DefaultItem.class);
    }

    /**
     * Gets all instances of Item from the ontology.
     */
    public Collection<? extends Item> getAllItemInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_ITEM, DefaultItem.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IzharLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IzharLetter", IzharLetter.class, DefaultIzharLetter.class);
    }

    /**
     * Creates an instance of type IzharLetter.  Modifies the underlying ontology.
     */
    public IzharLetter createIzharLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IZHARLETTER, DefaultIzharLetter.class);
    }

    /**
     * Gets an instance of type IzharLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IzharLetter getIzharLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IZHARLETTER, DefaultIzharLetter.class);
    }

    /**
     * Gets all instances of IzharLetter from the ontology.
     */
    public Collection<? extends IzharLetter> getAllIzharLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IZHARLETTER, DefaultIzharLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#IzharShafawiLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#IzharShafawiLetter", IzharShafawiLetter.class, DefaultIzharShafawiLetter.class);
    }

    /**
     * Creates an instance of type IzharShafawiLetter.  Modifies the underlying ontology.
     */
    public IzharShafawiLetter createIzharShafawiLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_IZHARSHAFAWILETTER, DefaultIzharShafawiLetter.class);
    }

    /**
     * Gets an instance of type IzharShafawiLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public IzharShafawiLetter getIzharShafawiLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_IZHARSHAFAWILETTER, DefaultIzharShafawiLetter.class);
    }

    /**
     * Gets all instances of IzharShafawiLetter from the ontology.
     */
    public Collection<? extends IzharShafawiLetter> getAllIzharShafawiLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_IZHARSHAFAWILETTER, DefaultIzharShafawiLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Letter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Letter", Letter.class, DefaultLetter.class);
    }

    /**
     * Creates an instance of type Letter.  Modifies the underlying ontology.
     */
    public Letter createLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_LETTER, DefaultLetter.class);
    }

    /**
     * Gets an instance of type Letter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Letter getLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_LETTER, DefaultLetter.class);
    }

    /**
     * Gets all instances of Letter from the ontology.
     */
    public Collection<? extends Letter> getAllLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_LETTER, DefaultLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#LetterOccurrence
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#LetterOccurrence", LetterOccurrence.class, DefaultLetterOccurrence.class);
    }

    /**
     * Creates an instance of type LetterOccurrence.  Modifies the underlying ontology.
     */
    public LetterOccurrence createLetterOccurrence(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_LETTEROCCURRENCE, DefaultLetterOccurrence.class);
    }

    /**
     * Gets an instance of type LetterOccurrence with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public LetterOccurrence getLetterOccurrence(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_LETTEROCCURRENCE, DefaultLetterOccurrence.class);
    }

    /**
     * Gets all instances of LetterOccurrence from the ontology.
     */
    public Collection<? extends LetterOccurrence> getAllLetterOccurrenceInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_LETTEROCCURRENCE, DefaultLetterOccurrence.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#MaadLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#MaadLetter", MaadLetter.class, DefaultMaadLetter.class);
    }

    /**
     * Creates an instance of type MaadLetter.  Modifies the underlying ontology.
     */
    public MaadLetter createMaadLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_MAADLETTER, DefaultMaadLetter.class);
    }

    /**
     * Gets an instance of type MaadLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public MaadLetter getMaadLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_MAADLETTER, DefaultMaadLetter.class);
    }

    /**
     * Gets all instances of MaadLetter from the ontology.
     */
    public Collection<? extends MaadLetter> getAllMaadLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_MAADLETTER, DefaultMaadLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#MeemSakinah
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#MeemSakinah", MeemSakinah.class, DefaultMeemSakinah.class);
    }

    /**
     * Creates an instance of type MeemSakinah.  Modifies the underlying ontology.
     */
    public MeemSakinah createMeemSakinah(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_MEEMSAKINAH, DefaultMeemSakinah.class);
    }

    /**
     * Gets an instance of type MeemSakinah with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public MeemSakinah getMeemSakinah(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_MEEMSAKINAH, DefaultMeemSakinah.class);
    }

    /**
     * Gets all instances of MeemSakinah from the ontology.
     */
    public Collection<? extends MeemSakinah> getAllMeemSakinahInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_MEEMSAKINAH, DefaultMeemSakinah.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#NoonSakinahAndTanween
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#NoonSakinahAndTanween", NoonSakinahAndTanween.class, DefaultNoonSakinahAndTanween.class);
    }

    /**
     * Creates an instance of type NoonSakinahAndTanween.  Modifies the underlying ontology.
     */
    public NoonSakinahAndTanween createNoonSakinahAndTanween(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_NOONSAKINAHANDTANWEEN, DefaultNoonSakinahAndTanween.class);
    }

    /**
     * Gets an instance of type NoonSakinahAndTanween with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public NoonSakinahAndTanween getNoonSakinahAndTanween(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_NOONSAKINAHANDTANWEEN, DefaultNoonSakinahAndTanween.class);
    }

    /**
     * Gets all instances of NoonSakinahAndTanween from the ontology.
     */
    public Collection<? extends NoonSakinahAndTanween> getAllNoonSakinahAndTanweenInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_NOONSAKINAHANDTANWEEN, DefaultNoonSakinahAndTanween.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Qalqalah
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Qalqalah", Qalqalah.class, DefaultQalqalah.class);
    }

    /**
     * Creates an instance of type Qalqalah.  Modifies the underlying ontology.
     */
    public Qalqalah createQalqalah(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_QALQALAH, DefaultQalqalah.class);
    }

    /**
     * Gets an instance of type Qalqalah with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Qalqalah getQalqalah(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_QALQALAH, DefaultQalqalah.class);
    }

    /**
     * Gets all instances of Qalqalah from the ontology.
     */
    public Collection<? extends Qalqalah> getAllQalqalahInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_QALQALAH, DefaultQalqalah.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#QalqalahLetter
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#QalqalahLetter", QalqalahLetter.class, DefaultQalqalahLetter.class);
    }

    /**
     * Creates an instance of type QalqalahLetter.  Modifies the underlying ontology.
     */
    public QalqalahLetter createQalqalahLetter(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_QALQALAHLETTER, DefaultQalqalahLetter.class);
    }

    /**
     * Gets an instance of type QalqalahLetter with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public QalqalahLetter getQalqalahLetter(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_QALQALAHLETTER, DefaultQalqalahLetter.class);
    }

    /**
     * Gets all instances of QalqalahLetter from the ontology.
     */
    public Collection<? extends QalqalahLetter> getAllQalqalahLetterInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_QALQALAHLETTER, DefaultQalqalahLetter.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#RuleOccurrence
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#RuleOccurrence", RuleOccurrence.class, DefaultRuleOccurrence.class);
    }

    /**
     * Creates an instance of type RuleOccurrence.  Modifies the underlying ontology.
     */
    public RuleOccurrence createRuleOccurrence(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_RULEOCCURRENCE, DefaultRuleOccurrence.class);
    }

    /**
     * Gets an instance of type RuleOccurrence with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public RuleOccurrence getRuleOccurrence(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_RULEOCCURRENCE, DefaultRuleOccurrence.class);
    }

    /**
     * Gets all instances of RuleOccurrence from the ontology.
     */
    public Collection<? extends RuleOccurrence> getAllRuleOccurrenceInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_RULEOCCURRENCE, DefaultRuleOccurrence.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Rules
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Rules", Rules.class, DefaultRules.class);
    }

    /**
     * Creates an instance of type Rules.  Modifies the underlying ontology.
     */
    public Rules createRules(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_RULES, DefaultRules.class);
    }

    /**
     * Gets an instance of type Rules with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Rules getRules(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_RULES, DefaultRules.class);
    }

    /**
     * Gets all instances of Rules from the ontology.
     */
    public Collection<? extends Rules> getAllRulesInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_RULES, DefaultRules.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Tanween
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Tanween", Tanween.class, DefaultTanween.class);
    }

    /**
     * Creates an instance of type Tanween.  Modifies the underlying ontology.
     */
    public Tanween createTanween(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_TANWEEN, DefaultTanween.class);
    }

    /**
     * Gets an instance of type Tanween with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Tanween getTanween(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_TANWEEN, DefaultTanween.class);
    }

    /**
     * Gets all instances of Tanween from the ontology.
     */
    public Collection<? extends Tanween> getAllTanweenInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_TANWEEN, DefaultTanween.class);
    }


    /* ***************************************************
     * Class http://www.tajweedontology.org/ontologies/rules#Word
     */

    {
        javaMapping.add("http://www.tajweedontology.org/ontologies/rules#Word", Word.class, DefaultWord.class);
    }

    /**
     * Creates an instance of type Word.  Modifies the underlying ontology.
     */
    public Word createWord(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_WORD, DefaultWord.class);
    }

    /**
     * Gets an instance of type Word with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public Word getWord(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_WORD, DefaultWord.class);
    }

    /**
     * Gets all instances of Word from the ontology.
     */
    public Collection<? extends Word> getAllWordInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_WORD, DefaultWord.class);
    }


}