/**********************************************************************
Copyright (c) 2008 Erik Bengtson and others. All rights reserved.
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
package org.datanucleus.store.xml.binder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.xml.bind.annotation.XmlElementRef;

import org.datanucleus.ClassLoaderResolver;
import org.datanucleus.exceptions.ClassNotResolvedException;
import org.datanucleus.metadata.AbstractMemberMetaData;
import org.datanucleus.metadata.FieldRole;
import org.datanucleus.store.xml.XMLUtils;

public class XmlElementRefHandler implements InvocationHandler
{
    private ClassLoaderResolver clr;
    private AbstractMemberMetaData ammd;

    public XmlElementRefHandler(AbstractMemberMetaData ammd, ClassLoaderResolver clr)
    {
        this.ammd = ammd;
        this.clr = clr;
    }

    public static Annotation newProxy(AbstractMemberMetaData ammd, ClassLoaderResolver clr)
    {
        return (Annotation) Proxy.newProxyInstance(AbstractMemberMetaData.class.getClassLoader(), 
            new Class[]{XmlElementRef.class}, new XmlElementRefHandler(ammd, clr));
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        String name = method.getName();
        if (name.equals("getClassValue"))
        {
            name = (String) args[1];
        }

        if (name.equals("annotationType"))
        {
            return XmlElementRef.class;
        }
        else if (name.equals("namespace"))
        {
            String value = "##default";
            if (ammd.hasExtension("namespace"))
            {
                value = ammd.getValueForExtension("namespace");
            }
            return value;
        }
        else if (name.equals("name"))
        {
            return XMLUtils.getElementNameForMember(ammd, FieldRole.ROLE_FIELD);
        }
        else if (name.equals("type"))
        {
            if (ammd.hasExtension("type"))
            {
                try
                {
                    return clr.classForName(ammd.getValueForExtension("type"));
                }
                catch (ClassNotResolvedException cnre)
                {
                    throw new RuntimeException(cnre.getMessage());
                }
            }
            return XmlElementRef.DEFAULT.class;
        }
        return null;
    }
}