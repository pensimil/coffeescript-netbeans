/*
 * Copyright(c) Zdenek Tronicek, FIT CTU in Prague. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (CDDL). You may not use this file except in
 * compliance with the CDDL. You can obtain a copy of the CDDL at
 * http://www.netbeans.org/cddl.html.
 *
 */
package coffeescript.nb.indexing;

import coffeescript.nb.antlr.parser.definitions.Definition;
import coffeescript.nb.core.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import org.netbeans.api.project.Project;
import org.netbeans.modules.parsing.spi.indexing.support.IndexResult;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;

/**
 *
 * @author Denis Stepanov
 */
public class CoffeeScriptIndex {

    public static final String CLASS_KEY = "CLASS_KEY"; //NOI18N
    public static final String FIELD_KEY = "FIELD_KEY"; //NOI18N
    public static final String METHOD_KEY = "METHOD_KEY"; //NOI18N
    public static final String CLASS_FIELD_KEY = "CLASS_FIELD_KEY"; //NOI18N
    public static final String CLASS_METHOD_KEY = "CLASS_METHOD_KEY"; //NOI18N
    public static final String METHOD_PARAM_KEY = "METHOD_PARAM_KEY"; //NOI18N
    public static final String ROOT_METHOD_KEY = "ROOT_METHOD_KEY"; //NOI18N
    public static final String ROOT_CLASS_KEY = "ROOT_CLASS_KEY"; //NOI18N
    public static final String ROOT_FIELD_KEY = "ROOT_FIELD_KEY"; //NOI18N

    public static CoffeeScriptIndex create(Project project) {
        try {
            return new CoffeeScriptIndex(project);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
    private final QuerySupport querySupport;

    private CoffeeScriptIndex(Project project) throws IOException {
        Collection<FileObject> sourceRoots = QuerySupport.findRoots(project,
                null,
                Collections.<String>emptyList(),
                Collections.<String>emptyList());
        this.querySupport = QuerySupport.forRoots(CoffeeScriptIndexer.Factory.NAME, CoffeeScriptIndexer.Factory.VERSION, sourceRoots.toArray(new FileObject[]{}));
    }


    public Collection<Definition> getAllFieldsInFile(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(FIELD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(FIELD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(ROOT_FIELD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(ROOT_FIELD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(METHOD_PARAM_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(METHOD_PARAM_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getAllClassesInFile(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(ROOT_CLASS_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(ROOT_CLASS_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getClassFieldsInFile(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(CLASS_FIELD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(CLASS_FIELD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getClassMethodsInFile(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(CLASS_METHOD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(CLASS_METHOD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getAllMethodsInFile(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(METHOD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(METHOD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(ROOT_METHOD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(ROOT_METHOD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getAllRootMethodsFromOtherFiles(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(METHOD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(!indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(METHOD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(ROOT_METHOD_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(!indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(ROOT_METHOD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getAllRootFieldsFromOtherFiles(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(ROOT_FIELD_KEY, "", QuerySupport.Kind.PREFIX))) {       
                if(!indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(ROOT_FIELD_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
                
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }
    
    public Collection<Definition> getAllClassesFromOtherFiles(FileObject fo) {
        try {
            Collection<Definition> result = new LinkedList<Definition>();
            for (IndexResult indexResult : filterDeletedFiles(querySupport.query(ROOT_CLASS_KEY, "", QuerySupport.Kind.PREFIX))) {
                if(!indexResult.getFile().equals(fo)) {  
                    for(String value : indexResult.getValues(ROOT_CLASS_KEY))
                        result.add(IndexedDefinitionFactory.create(value, indexResult.getFile()));
                }
            }
            return result;
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }

    protected Collection<FileObject> returnAsFiles(Collection<? extends IndexResult> results) {
        Collection<FileObject> rngFiles = new LinkedList<FileObject>();
        for (IndexResult result : filterDeletedFiles(results)) {
            if (result.getFile().getMIMEType().equals(Constants.MIME_TYPE)) {
                rngFiles.add(result.getFile());
            }
        }
        return rngFiles;
    }

    private Collection<? extends IndexResult> filterDeletedFiles(Collection<? extends IndexResult> queryResult) {
        Collection<IndexResult> filtered = new ArrayList<IndexResult>();
        for (IndexResult result : queryResult) {
            if (result.getFile() != null) {
                filtered.add(result);
            }
        }
        return filtered;
    }
}
