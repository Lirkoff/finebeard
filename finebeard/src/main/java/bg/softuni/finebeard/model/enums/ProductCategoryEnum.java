package bg.softuni.finebeard.model.enums;

public enum ProductCategoryEnum {
    BEARD_OILS("Oils"),
    BEARD_BALMS("Balms"),
    BEARD_SHAMPOOS_AND_WASHES("Shampoos and Washes"),
    BEARD_CONDITIONERS("Conditioners"),
    COMBS_AND_BRUSHES("Combs and Brushes"),
    TRIMMERS_AND_SCISSORS("Trimmers and Scissors"),
    WAXES("Waxes"),
    KITS_AND_GIFT_SETS("Kits and Gift sets"),
    GROWTH_PRODUCTS("Growth products"),
    SHAVING_CREAMS_AND_GELS("Shaving creams and Gels"),
    AFTERSHAVES_AND_POST_SHAVE_CARE("Aftershaves and Post-Shave care"),
    ACCESSORIES("Accessories");

    private final String displayName;

    ProductCategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
