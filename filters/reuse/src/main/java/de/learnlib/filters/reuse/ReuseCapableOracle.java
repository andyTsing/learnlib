/* Copyright (C) 2013 TU Dortmund
 * This file is part of LearnLib, http://www.learnlib.de/.
 * 
 * LearnLib is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 3.0 as published by the Free Software Foundation.
 * 
 * LearnLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with LearnLib; if not, see
 * <http://www.gnu.de/documents/lgpl.en.html>.
 */
package de.learnlib.filters.reuse;

import net.automatalib.words.Word;

/**
 * Required interface for the {@link ReuseOracle}. An implementation
 * needs to provide the ability to answer queries with respect to a
 * system state class S and an input and must be able to reset to SUL
 * to an initial state.
 * 
 * The {@link ReuseOracle} decides whether a full membership query needs to
 * be answered including a reset (via {@link #processQuery(Word)}) or if
 * there is a system state available that is able to save the reset with
 * some prefix (via {@link #continueQuery(Word, Object)}).
 *  
 * @author Oliver Bauer <oliver.bauer@tu-dortmund.de>
 *
 * @param <S> system state class
 * @param <I> input symbol class
 * @param <O> output symbol class
 */
public interface ReuseCapableOracle<S,I,O> {
	public static final class QueryResult<S, O> {
		public final Word<O> output;
		public final S newState;
		public QueryResult(Word<O> output, S newState) {
			super();
			this.output = output;
			this.newState = newState;
		}
	}
	
	/**
	 * This method will be invoked whenever a system state s was found for reusage
	 * when a new membership query is processed. Please note that only a saved reset
	 * can be ensured.
	 * 
	 * @param trace The query to consider (mostly a real suffix from a membership query).
	 * @param s A system state that corresponds to an already answered prefix.
	 * @return A query result consisting of the output to the input and the resulting 
	 * 	system state.
	 */
	QueryResult<S, O> continueQuery(Word<I> trace, S s);
	
	/**
	 * An implementation needs to provide a fresh system state, process the whole
	 * query and return a {@link QueryResult} with the resulting system state 
	 * ({@link QueryResult#newState}) and the SUL output to that query 
	 * ({@link QueryResult#output}).
	 * 
	 * This method will be invoked if no available system state was found and can
	 * be seen as a 'normal membership query'.
	 * 
	 * @param trace The query to consider.
	 * @return A query result consisting of the output to the input and the resulting 
	 * 	system state.
	 */
	QueryResult<S, O> processQuery(Word<I> trace);
}
