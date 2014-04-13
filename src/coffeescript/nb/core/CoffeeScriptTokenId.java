/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coffeescript.nb.core;

import coffeescript.nb.antlr.lexer.TokenEnumLexer;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

/**
 *
 * @author milos
 */
public class CoffeeScriptTokenId implements TokenId {

    private static final Language<CoffeeScriptTokenId> LANGUAGE = CoffeeScriptLanguage.getLanguage();
    private TokenEnumLexer token;
    private final Integer id;
    private String tokenString;

    public CoffeeScriptTokenId(
            TokenEnumLexer token,
            Integer id) {
        this.token = token;
        this.id = id;
    }

    @Override
    public String name() {
        return token.name();
    }

    @Override
    public int ordinal() {
        return id;
    }

    @Override
    public String primaryCategory() {
        return token.getCategory().getName();
    }  
    
    public String fixedText() {
        return token.fixedText();
    }    

    public static Language<CoffeeScriptTokenId> getLanguage() {
        return LANGUAGE;
    }
    
    public TokenEnumLexer getTokenEnum() {
        return token;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TokenEnumLexer)
            return token.equals(obj);
        return super.equals(obj);
    }    
    
}
