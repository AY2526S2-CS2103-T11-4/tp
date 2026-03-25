package seedu.address.model.resident;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Resident} matches a fielded {@code find} query.
 *
 * <p>Matching rules:
 * <ul>
 *     <li>Name keywords are matched as case-insensitive full words.</li>
 *     <li>Phone keywords are matched as case-insensitive substrings.</li>
 *     <li>Unit number keywords are matched as case-insensitive substrings.</li>
 *     <li>Multiple keywords within the same field are combined using OR.</li>
 *     <li>Different fields are combined using AND.</li>
 *     <li>An empty keyword list for a field means that field does not constrain the match.</li>
 * </ul>
 *
 * <p>For example, a predicate built from name keyword {@code alex}, phone keyword {@code 9876},
 * and unit keyword {@code 02-25} matches only residents whose name matches {@code alex}, whose
 * phone number contains {@code 9876}, and whose unit number contains {@code 02-25}.</p>
 */
public class ResidentMatchesFindPredicate implements Predicate<Resident> {
    private final List<String> nameKeywords;
    private final List<String> phoneKeywords;
    private final List<String> unitKeywords;

    /**
     * Creates a predicate using the given field-specific search keywords.
     *
     * @param nameKeywords Name keywords to match as full words, case-insensitively.
     * @param phoneKeywords Phone keywords to match as substrings, case-insensitively.
     * @param unitKeywords Unit number keywords to match as substrings, case-insensitively.
     */
    public ResidentMatchesFindPredicate(List<String> nameKeywords,
                                        List<String> phoneKeywords,
                                        List<String> unitKeywords) {
        requireNonNull(nameKeywords);
        requireNonNull(phoneKeywords);
        requireNonNull(unitKeywords);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.phoneKeywords = List.copyOf(phoneKeywords);
        this.unitKeywords = List.copyOf(unitKeywords);
    }

    /**
     * Returns {@code true} if the given resident satisfies all configured field constraints.
     */
    @Override
    public boolean test(Resident resident) {
        requireNonNull(resident);
        return matchesName(resident) && matchesPhone(resident) && matchesUnit(resident);
    }

    /**
     * Returns {@code true} if the resident matches at least one configured name keyword,
     * or if no name keywords were provided.
     */
    private boolean matchesName(Resident resident) {
        if (nameKeywords.isEmpty()) {
            return true;
        }

        return nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(resident.getName().fullName, keyword));
    }

    /**
     * Returns {@code true} if the resident's phone number contains at least one configured phone keyword,
     * or if no phone keywords were provided.
     */
    private boolean matchesPhone(Resident resident) {
        if (phoneKeywords.isEmpty()) {
            return true;
        }

        return phoneKeywords.stream()
                .anyMatch(keyword -> containsIgnoreCase(resident.getPhone().value, keyword));
    }

    /**
     * Returns {@code true} if the resident's unit number contains at least one configured unit keyword,
     * or if no unit keywords were provided.
     */
    private boolean matchesUnit(Resident resident) {
        if (unitKeywords.isEmpty()) {
            return true;
        }

        return unitKeywords.stream()
                .anyMatch(keyword -> containsIgnoreCase(resident.getUnitNumber().value, keyword));
    }

    /**
     * Returns {@code true} if {@code value} contains {@code keyword}, ignoring case.
     */
    private boolean containsIgnoreCase(String value, String keyword) {
        return value.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ResidentMatchesFindPredicate)) {
            return false;
        }

        ResidentMatchesFindPredicate otherPredicate = (ResidentMatchesFindPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && phoneKeywords.equals(otherPredicate.phoneKeywords)
                && unitKeywords.equals(otherPredicate.unitKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("phoneKeywords", phoneKeywords)
                .add("unitKeywords", unitKeywords)
                .toString();
    }
}
