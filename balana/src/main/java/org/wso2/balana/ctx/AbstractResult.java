/*
*  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/


package org.wso2.balana.ctx;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.balana.*;
import org.wso2.balana.xacml3.Obligation;
import org.wso2.balana.xacml3.advice.Advice;

import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract Represents the ResultType XML object from the Context schema. Any number
 * of these may included in a <code>ResponseCtx</code>. This class provides an abstract method to
 * encode XACML result with the decision effect, as well as an optional status data and obligations
 * and advices. encode method must be implemented under the concert class. 
 *
 */
public abstract class AbstractResult {

    /**
     * The decision to permit the request
     */
    public static final int DECISION_PERMIT = 0;

    /**
     * The decision to deny the request
     */
    public static final int DECISION_DENY = 1;

    /**
     * The decision that a decision about the request cannot be made
     */
    public static final int DECISION_INDETERMINATE = 2;

    /**
     * The decision that nothing applied to us
     */
    public static final int DECISION_NOT_APPLICABLE = 3;

    /**
     * string versions of the 4 Decision types used for encoding
     */
    public static final String[] DECISIONS = { "Permit", "Deny", "Indeterminate", "NotApplicable" };

    /**
     * set of obligations which may be empty
     */
    protected Set<ObligationResult> obligations;

    /**
     * set of advices which may be empty
     */
    protected Set<Advice> advices;

    /**
     * the decision effect
     */
    protected int decision = -1;

    /**
     * the status data
     */
    protected Status status = null;


    /**
     * Constructs a <code>AbstractResult</code> object with decision status data, obligations, advices
     *  and evaluation ctx
     *
     * @param decision the decision effect to include in this result. This must be one of the four
     *            fields in this class.
     * @param status the <code>Status</code> to include in this result
     * @param obligationResults a set of <code>ObligationResult</code> objects
     * @param advices  a set of <code>Advice</code> objects
     * @param evaluationCtx  <code>EvaluationCtx</code> object  
     * @throws IllegalArgumentException if decision is not valid
     */
    public AbstractResult(int decision, Status status, Set<ObligationResult> obligationResults,
                  Set<Advice> advices, EvaluationCtx evaluationCtx) throws IllegalArgumentException {

        // check that decision is valid
        if ((decision != DECISION_PERMIT) && (decision != DECISION_DENY)
                && (decision != DECISION_INDETERMINATE) && (decision != DECISION_NOT_APPLICABLE)) {
            throw new IllegalArgumentException("invalid decision value");
        }
        
        this.decision = decision;

        if(obligationResults != null){
            this.obligations = obligationResults;
        }

        if(advices != null){
            this.advices = advices;            
        }

        if (status == null){
            this.status = Status.getOkInstance();
        } else {
            this.status = status;
        }
    }

    /**
     * Returns the set of obligations that the PEP must fulfill, which may be empty.
     *
     * @return the set of <code>Obligation</code>
     */
    public Set<ObligationResult> getObligations() {
        if(obligations == null){
            obligations = new HashSet<ObligationResult>();
        }
        return obligations;
    }


    /**
     * Returns the set of advice that the PEP must fulfill, which may be empty.
     *
     * @return the set of <code>Advice</code>
     */
    public Set<Advice> getAdvices() {
        if(advices  == null){
            advices = new HashSet<Advice>();
        }
        return advices;
    }

    /**
     * Returns the decision associated with this <code>Result</code>. This will be one of the four
     * <code>DECISION_*</code> fields in this class.
     *
     * @return the decision effect
     */
    public int getDecision() {
        return decision;
    }

    /**
     * Returns the status data included in this <code>Result</code>. Typically this will be
     * <code>STATUS_OK</code> except when the decision is <code>INDETERMINATE</code>.
     *
     * @return status associated with this Result
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Encodes this <code>AbstractResult</code> into its XML form and writes this
     * out to the provided <code>OutputStream<code> with no indentation.
     *
     * @param output a stream into which the XML-encoded data is written
     */
    public void encode(OutputStream output) {
        encode(output, new Indenter(0));
    }

    /**
     * Encodes this <code>AbstractResult</code> into its XML form and writes this
     * out to the provided <code>OutputStream<code> with indentation.
     *
     * @param output a stream into which the XML-encoded data is written
     * @param indenter an object that creates indentation strings
     */
    public abstract void encode(OutputStream output, Indenter indenter);

}
