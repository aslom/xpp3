/*
 * $Header: /l/extreme/cvspub/XPP3/java/src/java/xpath/org/xmlpull/v1/builder/xpath/jaxen/XPathFunctionContext.java,v 1.2 2005/08/11 22:44:00 aslom Exp $
 * $Revision: 1.2 $
 * $Date: 2005/08/11 22:44:00 $
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
 * $Id: XPathFunctionContext.java,v 1.2 2005/08/11 22:44:00 aslom Exp $
 */


package org.xmlpull.v1.builder.xpath.jaxen;

import org.xmlpull.v1.builder.xpath.jaxen.function.*;
import org.xmlpull.v1.builder.xpath.jaxen.function.ext.*;


/** A <code>FunctionContext</code> implementing the core XPath
 *  function library, with extensions.
 *
 *  <p>
 *  The core XPath function library is provided through this
 *  implementation of <code>FunctionContext</code>.  Additionally,
 *  extension functions have been provided, as enumerated below.
 *  </p>
 *
 *  <p>
 *  This class implements a <i>Singleton</i> pattern (see {@link #getInstance}),
 *  as it is perfectly re-entrant and thread-safe.  If using the
 *  singleton, it is inadvisable to call {@link #registerFunction}
 *  as that will extend the global function context, affecting other
 *  users of the singleton.  But that's your call, really, now isn't
 *  it?  That may be what you really want to do.
 *  </p>
 *
 *  <p>
 *  Extension functions:
 *
 *  <ul>
 *     <li>matrix-concat(..)</li>
 *     <li>evaluate(..)</li>
 *  </ul>
 *
 *  @see FunctionContext
 *
 *  @author <a href="mailto:bob@werken.com">bob mcwhirter</a>
 */
public class XPathFunctionContext extends SimpleFunctionContext
{
   /** Singleton implementation.
    */
    private static class Singleton
    {
        /** Singleton instance.
         */
        private static XPathFunctionContext instance = new XPathFunctionContext();
    }

    /** Retrieve the singleton instance.
     *
     *  @return The singleton instance.
     */
    public static FunctionContext getInstance()
    {
        return Singleton.instance;
    }

    /** Construct.
     *
     *  <p>
     *  Construct with all core XPath functions registered.
     *  </p>
     */
    public XPathFunctionContext()
    {
        registerFunction( null,  // namespace URI
                          "boolean",
                          new BooleanFunction() );

        registerFunction( null,  // namespace URI
                          "ceiling",
                          new CeilingFunction() );

        registerFunction( null,  // namespace URI
                          "concat",
                          new ConcatFunction() );

        registerFunction( null,  // namespace URI
                          "contains",
                          new ContainsFunction() );
        
        registerFunction( null,  // namespace URI
                          "count",
                          new CountFunction() );

        registerFunction( null,  // namespace URI
                          "document",
                          new DocumentFunction() );

        registerFunction( null,  // namespace URI
                          "false",
                          new FalseFunction() );

        registerFunction( null,  // namespace URI
                          "floor",
                          new FloorFunction() );

        registerFunction( null,  // namespace URI
                          "id",
                          new IdFunction() );

        registerFunction( null,  // namespace URI
                          "last",
                          new LastFunction() );

        registerFunction( null,  // namespace URI
                          "local-name",
                          new LocalNameFunction() );

        registerFunction( null,  // namespace URI
                          "name",
                          new NameFunction() );

        registerFunction( null,  // namespace URI
                          "namespace-uri",
                          new NamespaceUriFunction() );

        registerFunction( null,  // namespace URI
                          "normalize-space",
                          new NormalizeSpaceFunction() );

        registerFunction( null,  // namespace URI
                          "not",
                          new NotFunction() );

        registerFunction( null,  // namespace URI
                          "number",
                          new NumberFunction() );

        registerFunction( null,  // namespace URI
                          "position",
                          new PositionFunction() );

        registerFunction( null,  // namespace URI
                          "round",
                          new RoundFunction() );

        registerFunction( null,  // namespace URI
                          "starts-with",
                          new StartsWithFunction() );

        registerFunction( null,  // namespace URI
                          "string",
                          new StringFunction() );

        registerFunction( null,  // namespace URI
                          "string-length",
                          new StringLengthFunction() );

        registerFunction( null,  // namespace URI
                          "substring-after",
                          new SubstringAfterFunction() );

        registerFunction( null,  // namespace URI
                          "substring-before",
                          new SubstringBeforeFunction() );

        registerFunction( null,  // namespace URI
                          "substring",
                          new SubstringFunction() );

        registerFunction( null,  // namespace URI
                          "sum",
                          new SumFunction() );

        registerFunction( null,  // namespace URI
                          "true",
                          new TrueFunction() );
        
        registerFunction( null,  // namespace URI
                          "translate",
                          new TranslateFunction() );
        

        // register extension functions
        // extension functions should go into a namespace, but which one?
        // for now, keep them in default namespace to not to break any code

        registerFunction( null,  // namespace URI
                          "matrix-concat",
                          new MatrixConcatFunction() );

        registerFunction( null,  // namespace URI
                          "evaluate",
                          new EvaluateFunction() );
        
        registerFunction( null,  // namespace URI
                          "lower-case",
                          new LowerFunction() );
        
        registerFunction( null,  // namespace URI
                          "upper-case",
                          new UpperFunction() );
        
        registerFunction( null,  // namespace URI
                          "ends-with",
                          new EndsWithFunction() );
        
    }
}
