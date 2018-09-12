package org.gangel.playground.equality;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class StringEquality {

    @Rule
    public TestName testName = new TestName();

    @Before
    public void showTestName() {
        System.out.println(testName.getMethodName());
    }

    /**
     * 
     */
    @Test
    public void testSameStringsEquality() {
        final String str1 = "Welcome to the jungle";
        final String str2 = new String("Welcome to the jungle");

        System.out.println("Strings equality : " + (str1.equals(str2)));
        System.out.println("Strings identify : " + (str1 == str2));
        
        assertThat(str1).isEqualTo(str2);
        assertFalse(str1 == str2);
    }

    @Test
    public void testSameStringsEqualityCompilerOptimization() {
        final String str1 = "Welcome to the jungle";
        final String str2 = "Welcome to the jungle";

        System.out.println("Strings equality : " + (str1.equals(str2)));
        System.out.println("Strings identify : " + (str1 == str2));
        assertThat(str1).isEqualTo(str2);
        assertTrue(str1 == str2);
    }
    
    @Test
    public void testDifferentStringInterned() {
        final String str1 = "Welcome to the jungle";
        final String str2 = new String("Welcome to the jungle").intern();
        
        assertThat(str1).isEqualTo(str2);
        assertTrue(str1 == str2);
    }

}
