package edu.nyu.cs.cs2580;

import java.util.HashMap;
import java.util.Vector;

public class NormalizedTFIDF {

	private static Ranker _ranker;

	public NormalizedTFIDF(Ranker ranker) {
		_ranker = ranker;
	}

	/* Added by Ravi */
	/**
	 * This function calculates term Frequency Vector (tfik component)
	 * 
	 * @param did
	 * @param query
	 * @return
	 */
	public Vector<Double> termFrequencyVector(int did, Vector<String> query) {
		Vector<Double> tfik = new Vector<Double>();
		double termFreq = 0.0;
		double wordCount = 0.0;
		for (int i = 0; i < query.size(); i++) {
			termFreq = (double) _ranker.termFreqInDoc(did, query.get(i));
			wordCount = (double) _ranker.wordCountInDoc(did);
			tfik.add(termFreq / wordCount);
		}
		return tfik;
	}

	/**
	 * This function calculates the inverse Document Frequency Vector(idf
	 * component)
	 * 
	 * @param terms
	 * @return
	 */
	public Vector<Double> invDocFreqVector(Vector<String> terms) {
		Vector<Double> idfk = new Vector<Double>();
		double docCount = 0;
		double docFreq = 0;
		double countByFreq = 0;
		double idfkValue = 0.0;
		for (int i = 0; i < terms.size(); i++) {
			docCount = (double) _ranker.documentCount();
			docFreq = (double) _ranker.documentFrequency(terms.get(i));
			countByFreq = docCount / docFreq;
			idfkValue = 1 + (Math.log(countByFreq) / Math.log(2));
			idfk.add(idfkValue);
		}
		return idfk;
	}

	/**
	 * This function calculates the inverse Document Frequency Vector(idf
	 * component)
	 * 
	 * @param terms
	 * @return
	 */
	public HashMap<String, Double> invDocFreqVector(
			HashMap<String, Double> terms) {
		HashMap<String, Double> idfk = new HashMap<String, Double>();
		double docCount = 0;
		double docFreq = 0;
		double countByFreq = 0;
		double idfkValue = 0.0;

		docCount = (double) _ranker.documentCount();
		for (String term : terms.keySet()) {
			docFreq = (double) _ranker.documentFrequency(term);
			countByFreq = docCount / docFreq;
			idfkValue = 1 + (Math.log(countByFreq) / Math.log(2));
			idfk.put(term, idfkValue);
		}

		return idfk;
	}

	/**
	 * This function calculates the tf * idf vector
	 * 
	 * @param did
	 * @param query
	 * @return
	 */
	public Vector<Double> tfidfVector(int did, Vector<String> query) {
		Vector<Double> termFreqV = termFrequencyVector(did, query);
		Vector<Double> invDocFreqV = invDocFreqVector(query);
		Vector<Double> tfidfVector = new Vector<Double>();
		for (int i = 0; i < termFreqV.size(); i++) {
			tfidfVector.add(termFreqV.get(i) * invDocFreqV.get(i));
		}
		return tfidfVector;
	}

	/**
	 * This function normalizes the above td * idf vector
	 * 
	 * @param tfIdfV
	 * @return
	 */
	public Vector<Double> normalizedTfIdfVector(Vector<Double> tfIdfV) {
		double l2form = 0.0;
		double interim = 0.0;
		Vector<Double> normalizedV = new Vector<Double>();
		for (int i = 0; i < tfIdfV.size(); i++) {
			interim += (tfIdfV.get(i) * tfIdfV.get(i));
		}
		l2form = Math.sqrt(interim);
		for (int j = 0; j < tfIdfV.size(); j++) {
			normalizedV.add(tfIdfV.get(j) / l2form);
		}
		return normalizedV;
	}

	// TODO : Write a function here to obtain Normailized tf * idf vectors for
	// all documents
	// TODO : Write a function here to obtain Normailized tf * idf vectors for
	// all queries -- Inquire how?
}
