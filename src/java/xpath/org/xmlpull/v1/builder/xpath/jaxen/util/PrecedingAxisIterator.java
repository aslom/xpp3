/*
 * $Header: /l/extreme/cvspub/XPP3/java/src/java/xpath/org/xmlpull/v1/builder/xpath/jaxen/util/PrecedingAxisIterator.java,v 1.1 2004/06/16 15:55:42 aslom Exp $
 * $Revision: 1.1 $
 * $Date: 2004/06/16 15:55:42 $
 *
 * ====================================================================
 *
 * Copyright (C) 2000-2002 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions, and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions, and the disclaimer that follows 
 *    these conditions in the documentation and/or other materials 
 *    provided with the distribution.
 *
 * 3. The name "Jaxen" must not be used to endorse or promote products
 *    derived from this software without prior written permission.  For
 *    written permission, please contact license@jaxen.org.
 * 
 * 4. Products derived from this software may not be called "Jaxen", nor
 *    may "Jaxen" appear in their name, without prior written permission
 *    from the Jaxen Project Management (pm@jaxen.org).
 * 
 * In addition, we request (but do not require) that you include in the 
 * end-user documentation provided with the redistribution and/or in the 
 * software itself an acknowledgement equivalent to the following:
 *     "This product includes software developed by the
 *      Jaxen Project (http://www.jaxen.org/)."
 * Alternatively, the acknowledgment may be graphical using the logos 
 * available at http://www.jaxen.org/
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE Jaxen AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * ====================================================================
 * This software consists of voluntary contributions made by many 
 * individuals on behalf of the Jaxen Project and was originally 
 * created by bob mcwhirter <bob@werken.com> and 
 * James Strachan <jstrachan@apache.org>.  For more information on the 
 * Jaxen Project, please see <http://www.jaxen.org/>.
 * 
 * $Id: PrecedingAxisIterator.java,v 1.1 2004/06/16 15:55:42 aslom Exp $
 */

package org.xmlpull.v1.builder.xpath.jaxen.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.xmlpull.v1.builder.xpath.jaxen.Navigator;
import org.xmlpull.v1.builder.xpath.jaxen.UnsupportedAxisException;

/**
 * @author Erwin Bolwidt
 */
public class PrecedingAxisIterator implements Iterator
{
    private final class ReverseDescendantOrSelfAxisIterator extends StackedIterator
    {
        ReverseDescendantOrSelfAxisIterator(Object contextNode)
            throws UnsupportedAxisException
        {
            pushIterator(PrecedingAxisIterator.this.navigator.getSelfAxisIterator(contextNode));
            init(contextNode, PrecedingAxisIterator.this.navigator);
        }
        
        protected Iterator createIterator(Object contextNode) 
        {
            try
            {
                Iterator iter = PrecedingAxisIterator.this.navigator.getChildAxisIterator(contextNode);

                if (iter == null)
                {
                    return null;
                }

                LinkedList reverse = new LinkedList();

                while ( iter.hasNext() )
                {
                    reverse.addFirst( iter.next() );
                }

                return reverse.iterator();
            }
            catch (UnsupportedAxisException e)
            {
                // okay...
            }
            return null;
        }
    }

    private final static Iterator EMPTY_ITERATOR = Collections.EMPTY_LIST.iterator();

    private Object contextNode;

    private Navigator navigator;

    private Iterator siblings;

    private Iterator currentSibling;

    public PrecedingAxisIterator(Object contextNode,
                                 Navigator navigator) throws UnsupportedAxisException
    {
        this.contextNode    = contextNode;
        this.navigator      = navigator;        
        this.siblings       = navigator.getPrecedingSiblingAxisIterator(contextNode);
        this.currentSibling = EMPTY_ITERATOR;
    }

    private boolean goBack()
    {
        while ( ! siblings.hasNext() )
        {
            if (!goUp())
            {
                return false;
            }
        }

        Object prevSibling = siblings.next();

        try
        {
            this.currentSibling = new ReverseDescendantOrSelfAxisIterator( prevSibling );
            return true;
        }
        catch (UnsupportedAxisException e)
        {
            return false;
        }
    }

    private boolean goUp()
    {
        if ( contextNode == null
             ||
             navigator.isDocument(contextNode) )
        {
            return false;
        }

        try
        {
            contextNode = navigator.getParentNode(contextNode);

            if ( contextNode != null
                 &&
                 ! navigator.isDocument(contextNode) )
            {
                siblings = navigator.getPrecedingSiblingAxisIterator(contextNode);

                return true;
            }

            return false;
        }
        catch (UnsupportedAxisException e)
        {
            // Appearantly, the preceding-siblings axis is not supported
            // for the parent node, so the iterator can't go up in the
            // ancestry anymore.
            return false;
        }
    }

    public boolean hasNext()
    {
        while ( ! currentSibling.hasNext() )
        {
            if ( ! goBack() )
            {
                return false;
            }
        }

        return true;
    }

    public Object next() throws NoSuchElementException
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        return currentSibling.next();
    }

    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
