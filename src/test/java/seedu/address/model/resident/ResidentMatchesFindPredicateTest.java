package seedu.address.model.resident;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ResidentBuilder;

public class ResidentMatchesFindPredicateTest {

    @Test
    public void equals() {
        ResidentMatchesFindPredicate firstPredicate =
                new ResidentMatchesFindPredicate(List.of("alex"), List.of("9876"), List.of("02-25"));
        ResidentMatchesFindPredicate secondPredicate =
                new ResidentMatchesFindPredicate(List.of("alex"), List.of(), List.of());

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new ResidentMatchesFindPredicate(List.of("alex"),
                List.of("9876"), List.of("02-25"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchesConfiguredFields_returnsTrue() {
        Resident resident = new ResidentBuilder().withName("Alex Tan").withPhone("98761234")
                .withUnitNumber("Block B #02-25").build();

        assertTrue(new ResidentMatchesFindPredicate(List.of("alex"), List.of(), List.of()).test(resident));
        assertTrue(new ResidentMatchesFindPredicate(List.of(), List.of("9876"), List.of()).test(resident));
        assertTrue(new ResidentMatchesFindPredicate(List.of(), List.of(), List.of("02-25")).test(resident));
        assertTrue(new ResidentMatchesFindPredicate(List.of("alex"), List.of("9876"), List.of("02-25"))
                .test(resident));
    }

    @Test
    public void test_sameFieldUsesOrDifferentFieldsUseAnd() {
        Resident resident = new ResidentBuilder().withName("Alex Tan").withPhone("98761234")
                .withUnitNumber("Block B #02-25").build();

        assertTrue(new ResidentMatchesFindPredicate(List.of("alex", "brenda"), List.of(), List.of())
                .test(resident));
        assertTrue(new ResidentMatchesFindPredicate(List.of(), List.of("1111", "9876"), List.of())
                .test(resident));
        assertFalse(new ResidentMatchesFindPredicate(List.of("alex"), List.of("1111"), List.of())
                .test(resident));
    }

    @Test
    public void test_nonMatchingFields_returnsFalse() {
        Resident resident = new ResidentBuilder().withName("Alex Tan").withPhone("98761234")
                .withUnitNumber("Block B #02-25").build();

        assertFalse(new ResidentMatchesFindPredicate(List.of("brenda"), List.of(), List.of()).test(resident));
        assertFalse(new ResidentMatchesFindPredicate(List.of(), List.of("5555"), List.of()).test(resident));
        assertFalse(new ResidentMatchesFindPredicate(List.of(), List.of(), List.of("03-30")).test(resident));
    }

    @Test
    public void toStringMethod() {
        ResidentMatchesFindPredicate predicate =
                new ResidentMatchesFindPredicate(List.of("alex"), List.of("9876"), List.of("02-25"));

        String expected = ResidentMatchesFindPredicate.class.getCanonicalName()
                + "{nameKeywords=[alex], phoneKeywords=[9876], unitKeywords=[02-25]}";
        assertEquals(expected, predicate.toString());
    }
}
