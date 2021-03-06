/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/
package org.apache.synapse.samples.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class PropertyLoader {

    public static String CARBON_HOME;
    public static String HOST_NAME;
    public static String HTTPS_PORT;
    public static String NHTTP_PORT;
    public static String NHTTPS_PORT;
    public static String USER_NAME;
    public static String PASSWORD;
    public static String CONTEXT_ROOT;

    public static Properties properties = new Properties();
    public static InputStream inputStream = PropertyLoader.class.getResourceAsStream("/esb.properties");

    public static void getProperties() throws IOException {
        try {
            properties.load(inputStream);
            CARBON_HOME = properties.getProperty("carbon.home", "");
            HOST_NAME = properties.getProperty("host.name", "localhost");
            HTTPS_PORT = properties.getProperty("https.port", "9443");
            NHTTP_PORT = properties.getProperty("nhttp.port", "8280");
            NHTTPS_PORT = properties.getProperty("nhttps.port", "8243");
            USER_NAME = properties.getProperty("user.name", "admin");
            PASSWORD = properties.getProperty("admin.password", "admin");
            CONTEXT_ROOT = properties.getProperty("context.root", null);
        } catch (IOException e) {
            throw new IOException("Error reading esp.properties file : " + e);
        }

    }


}
