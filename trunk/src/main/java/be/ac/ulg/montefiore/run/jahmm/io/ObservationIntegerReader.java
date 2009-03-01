package be.ac.ulg.montefiore.run.jahmm.io;

import java.io.IOException;
import java.io.StreamTokenizer;

import be.ac.ulg.montefiore.run.jahmm.ObservationInteger;


/**
 * Reads an {@link be.ac.ulg.montefiore.run.jahmm.ObservationInteger
 * ObservationInteger} up to (and including) a semi-colon.
 * <p>
 * The format of this observation is a simple integer ([+-]?[0123456789]+).
 * <p>
 * For example, reading
 * <pre>76;</pre>
 * creates an observation such as the one generated by
 * <code>new ObservationInteger(76);</code>
 */
public class ObservationIntegerReader
extends ObservationReader<ObservationInteger>
{	
	// Maximum value of the obserations read + 1
	private int nbElements;
	
	
	/**
	 * Constructs a reader of {@link ObservationInteger ObservationInteger}.
	 */
	public ObservationIntegerReader()
	{
		nbElements = Integer.MAX_VALUE;
	}
	
	
	/**
	 * Constructs a reader of {@link ObservationInteger ObservationInteger}.
	 * Verifies the maximum value of the observations read.
	 *
	 * @param nbElements The permitted number of different elements.  Each
	 *        value read must be <tt>0 &le; value &lt; nbElements</tt>.
	 */
	public ObservationIntegerReader(int nbElements)
	{
		if (nbElements <= 0)
			throw new IllegalArgumentException("Nb of elements must be " +
			"positive");
		
		this.nbElements = nbElements;
	}
	
	
	/**
	 * Reads an {@link be.ac.ulg.montefiore.run.jahmm.ObservationInteger
	 * ObservationInteger} reader, as explained in 
	 * {@link ObservationReader ObservationReader}.
	 *
	 * @param st A stream tokenizer.
	 * @return The {@link be.ac.ulg.montefiore.run.jahmm.ObservationInteger
	 *         ObservationInteger} read.
	 */
	public ObservationInteger read(StreamTokenizer st) 
	throws IOException, FileFormatException
	{	
		ObservationInteger oi;
		
		st.ordinaryChar((int)'.');
		
		if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
			if (st.nval > nbElements)
				throw new FileFormatException(st.lineno(),
						"Integer higher than maximum value " + (nbElements-1));
			oi = new ObservationInteger((int) st.nval);
		} else
			throw new FileFormatException(st.lineno(), "Integer expected");
		
		if (st.nextToken() != (int) ';')
			throw new FileFormatException(st.lineno(), "';' expected");
		
		ObservationSequencesReader.initSyntaxTable(st);
		
		return oi;
	}
}
