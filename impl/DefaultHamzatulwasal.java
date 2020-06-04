package V5TajweedFactoryOnto.impl;

import V5TajweedFactoryOnto.*;


import java.net.URI;
import java.util.Collection;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultHamzatulwasal <br>
 * @version generated on Thu Jun 04 21:14:55 PKT 2020 by Ramsha
 */
public class DefaultHamzatulwasal extends WrappedIndividualImpl implements Hamzatulwasal {

    public DefaultHamzatulwasal(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#followedBy
     */
     
    public Collection<? extends WrappedIndividual> getFollowedBy() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_FOLLOWEDBY,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasFollowedBy() {
	   return !getFollowedBy().isEmpty();
    }

    public void addFollowedBy(WrappedIndividual newFollowedBy) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_FOLLOWEDBY,
                                       newFollowedBy);
    }

    public void removeFollowedBy(WrappedIndividual oldFollowedBy) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_FOLLOWEDBY,
                                          oldFollowedBy);
    }


    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#hasRuleOccurrence
     */
     
    public Collection<? extends WrappedIndividual> getHasRuleOccurrence() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASRULEOCCURRENCE,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasHasRuleOccurrence() {
	   return !getHasRuleOccurrence().isEmpty();
    }

    public void addHasRuleOccurrence(WrappedIndividual newHasRuleOccurrence) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASRULEOCCURRENCE,
                                       newHasRuleOccurrence);
    }

    public void removeHasRuleOccurrence(WrappedIndividual oldHasRuleOccurrence) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASRULEOCCURRENCE,
                                          oldHasRuleOccurrence);
    }


    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#hasRuleType
     */
     
    public Collection<? extends WrappedIndividual> getHasRuleType() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASRULETYPE,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasHasRuleType() {
	   return !getHasRuleType().isEmpty();
    }

    public void addHasRuleType(WrappedIndividual newHasRuleType) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASRULETYPE,
                                       newHasRuleType);
    }

    public void removeHasRuleType(WrappedIndividual oldHasRuleType) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASRULETYPE,
                                          oldHasRuleType);
    }


    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#involveHarakat
     */
     
    public Collection<? extends WrappedIndividual> getInvolveHarakat() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_INVOLVEHARAKAT,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasInvolveHarakat() {
	   return !getInvolveHarakat().isEmpty();
    }

    public void addInvolveHarakat(WrappedIndividual newInvolveHarakat) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_INVOLVEHARAKAT,
                                       newInvolveHarakat);
    }

    public void removeInvolveHarakat(WrappedIndividual oldInvolveHarakat) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_INVOLVEHARAKAT,
                                          oldInvolveHarakat);
    }


    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#involveLetter
     */
     
    public Collection<? extends WrappedIndividual> getInvolveLetter() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_INVOLVELETTER,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasInvolveLetter() {
	   return !getInvolveLetter().isEmpty();
    }

    public void addInvolveLetter(WrappedIndividual newInvolveLetter) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_INVOLVELETTER,
                                       newInvolveLetter);
    }

    public void removeInvolveLetter(WrappedIndividual oldInvolveLetter) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_INVOLVELETTER,
                                          oldInvolveLetter);
    }


    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#occurAt
     */
     
    public Collection<? extends WrappedIndividual> getOccurAt() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_OCCURAT,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasOccurAt() {
	   return !getOccurAt().isEmpty();
    }

    public void addOccurAt(WrappedIndividual newOccurAt) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_OCCURAT,
                                       newOccurAt);
    }

    public void removeOccurAt(WrappedIndividual oldOccurAt) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_OCCURAT,
                                          oldOccurAt);
    }


    /* ***************************************************
     * Object Property http://www.tajweedontology.org/ontologies/rules#precededBy
     */
     
    public Collection<? extends WrappedIndividual> getPrecededBy() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_PRECEDEDBY,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasPrecededBy() {
	   return !getPrecededBy().isEmpty();
    }

    public void addPrecededBy(WrappedIndividual newPrecededBy) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_PRECEDEDBY,
                                       newPrecededBy);
    }

    public void removePrecededBy(WrappedIndividual oldPrecededBy) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_PRECEDEDBY,
                                          oldPrecededBy);
    }


    /* ***************************************************
     * Data Property http://www.tajweedontology.org/ontologies/rules#hasHaraktPosition
     */
     
    public Collection<? extends Integer> getHasHaraktPosition() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASHARAKTPOSITION, Integer.class);
    }

    public boolean hasHasHaraktPosition() {
		return !getHasHaraktPosition().isEmpty();
    }

    public void addHasHaraktPosition(Integer newHasHaraktPosition) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASHARAKTPOSITION, newHasHaraktPosition);
    }

    public void removeHasHaraktPosition(Integer oldHasHaraktPosition) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASHARAKTPOSITION, oldHasHaraktPosition);
    }


    /* ***************************************************
     * Data Property http://www.tajweedontology.org/ontologies/rules#hasLetterPosition
     */
     
    public Collection<? extends Integer> getHasLetterPosition() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASLETTERPOSITION, Integer.class);
    }

    public boolean hasHasLetterPosition() {
		return !getHasLetterPosition().isEmpty();
    }

    public void addHasLetterPosition(Integer newHasLetterPosition) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASLETTERPOSITION, newHasLetterPosition);
    }

    public void removeHasLetterPosition(Integer oldHasLetterPosition) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASLETTERPOSITION, oldHasLetterPosition);
    }


    /* ***************************************************
     * Data Property http://www.tajweedontology.org/ontologies/rules#involveSurahNo
     */
     
    public Collection<? extends Integer> getInvolveSurahNo() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVESURAHNO, Integer.class);
    }

    public boolean hasInvolveSurahNo() {
		return !getInvolveSurahNo().isEmpty();
    }

    public void addInvolveSurahNo(Integer newInvolveSurahNo) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVESURAHNO, newInvolveSurahNo);
    }

    public void removeInvolveSurahNo(Integer oldInvolveSurahNo) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVESURAHNO, oldInvolveSurahNo);
    }


    /* ***************************************************
     * Data Property http://www.tajweedontology.org/ontologies/rules#involveVerseNo
     */
     
    public Collection<? extends Integer> getInvolveVerseNo() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEVERSENO, Integer.class);
    }

    public boolean hasInvolveVerseNo() {
		return !getInvolveVerseNo().isEmpty();
    }

    public void addInvolveVerseNo(Integer newInvolveVerseNo) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEVERSENO, newInvolveVerseNo);
    }

    public void removeInvolveVerseNo(Integer oldInvolveVerseNo) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEVERSENO, oldInvolveVerseNo);
    }


    /* ***************************************************
     * Data Property http://www.tajweedontology.org/ontologies/rules#involveWord
     */
     
    public Collection<? extends Object> getInvolveWord() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEWORD, Object.class);
    }

    public boolean hasInvolveWord() {
		return !getInvolveWord().isEmpty();
    }

    public void addInvolveWord(Object newInvolveWord) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEWORD, newInvolveWord);
    }

    public void removeInvolveWord(Object oldInvolveWord) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEWORD, oldInvolveWord);
    }


    /* ***************************************************
     * Data Property http://www.tajweedontology.org/ontologies/rules#involveWordNo
     */
     
    public Collection<? extends Integer> getInvolveWordNo() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEWORDNO, Integer.class);
    }

    public boolean hasInvolveWordNo() {
		return !getInvolveWordNo().isEmpty();
    }

    public void addInvolveWordNo(Integer newInvolveWordNo) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEWORDNO, newInvolveWordNo);
    }

    public void removeInvolveWordNo(Integer oldInvolveWordNo) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_INVOLVEWORDNO, oldInvolveWordNo);
    }


}
