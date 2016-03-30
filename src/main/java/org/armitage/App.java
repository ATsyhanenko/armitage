package org.armitage;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Like;
import facebook4j.Page;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FacebookException
    {
        System.out.println( "Hello World!" );
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.getOAuthAppAccessToken();

        facebook.setOAuthAccessToken(new AccessToken("CAACEdEose0cBAGZAZCcqT3FjODJ0AV6F6bYf6ZAPd0C8pzgxBsHtJ3lCiQ4EB9OCADelqjf8ZAmZCo466NnMbsqdmHWLHmtl0V4LA4NfM7ZCa2ehQlgfkGSZA92kM84iewdsNFSXNLA0lbawMNTTHNUuykx83lWUjXTiOYpdgdHL72CppJPVZBZAlgHqbfZBBFOI60C3yhMuKkrjVJjAaACT68"));
        ResponseList<Like> likeList = facebook.getUserLikes();
        System.out.println("\nLike count: "+likeList.size());

        for(Like l : likeList){
            System.out.println("like: cat"+l.getCategory()+" id"+l.getId()+" name"+l.getName()+" "+l.getMetadata());
        }
        
        String facebookUserName = "WoodenToaster";

        Page pgId = facebook.getPage(facebookUserName);
        
        likeList = facebook.getUserLikes(pgId.getId());
        System.out.println("\nLike count: "+likeList.size());

        for(Like l : likeList){
            System.out.println("{like} cat: "+l.getCategory()+" id: "+l.getId()+" name: "+l.getName()+" meta: "+l.getMetadata());
        }
        
        System.out.println(facebook.getPage("198158373583394"));
    }
}
