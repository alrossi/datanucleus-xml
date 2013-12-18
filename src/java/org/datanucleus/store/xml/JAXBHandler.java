/**********************************************************************
Copyright (c) 2013 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.datanucleus.store.xml;

import javax.xml.bind.JAXBException;

import org.datanucleus.ClassLoaderResolver;
import org.datanucleus.metadata.MetaDataManager;
import org.w3c.dom.Node;

/**
 * Interface to be implemented by whichever JAXB implementation we operate with.
 */
public interface JAXBHandler
{
    /**
     * Method to marshall an object into XML for storing.
     * @param obj The object
     * @param node The node where we store it
     * @param mmgr MetaData manager
     * @param clr ClassLoader resolver
     * @throws JAXBException
     */
    public void marshall(Object obj, Node node, MetaDataManager mmgr, ClassLoaderResolver clr) 
    throws JAXBException;

    /**
     * Method to unmarshall a node from XML into an object.
     * @param cls Type of object
     * @param node The node to be unmarshalled
     * @param mmgr MetaData manager
     * @param clr ClassLoader resolver
     * @throws JAXBException
     */
    public Object unmarshall(Class cls, Node node, MetaDataManager mmgr, ClassLoaderResolver clr) 
    throws JAXBException;
}