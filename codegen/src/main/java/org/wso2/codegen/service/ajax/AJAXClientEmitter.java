/*
 * Copyright 2005,2006 WSO2, Inc. http://www.wso2.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.codegen.service.ajax;
/*
 * 
 */

import org.apache.axis2.wsdl.codegen.CodeGenerationException;
import org.apache.axis2.wsdl.codegen.writer.FileWriter;
import org.apache.axis2.wsdl.codegen.emitter.AxisServiceBasedMultiLanguageEmitter;

import org.w3c.dom.Document;

import java.io.IOException;


public class AJAXClientEmitter extends AxisServiceBasedMultiLanguageEmitter {
    /**
     * emit stub been overwritten to write the interface implementation only
     *
     * @throws CodeGenerationException
     */
    public void emitStub() throws CodeGenerationException {
        try {
            //write interface implementations
            //for the html clients we are only generating the
            //the stub
            writeInterfaceImplementation();
        } catch (Exception e) {
            throw new CodeGenerationException(e);
        }
    }

    /**
     * Skeletons are not supported in the html client case
     *
     * @throws CodeGenerationException
     */
    public void emitSkeleton() throws CodeGenerationException {
        //we do not support emitting "skeltons" in html
        throw new CodeGenerationException(
                "Emitting skeletons is not supported for this language!");
    }

    /**
     * Write class overidden to avoid the packaging
     *
     * @param model
     * @param writer
     * @throws IOException
     * @throws Exception
     */
    protected void writeFile(Document model, FileWriter writer)
            throws IOException, Exception {
        writer.loadTemplate();

        String className = model.getDocumentElement().getAttribute("name");
        writer.createOutFile(null, className); //pass in null as the package

        // use the global resolver
        writer.parse(model, resolver);
    }
}

