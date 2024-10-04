package bg.softuni.finebeard.model.enums;



public enum BrandEnum {
    Beardbrand("Beardbrand"),
    Honest_Amish("Honest Amish"),
    Mountaineer_Brand("Mountaineer Brand"),
    The_Art_of_Shaving("The Art of Shaving"),
    Viking_Revolution("Viking Revolution"),
    Jack_Black("Jack Black"),
    Bossman("Bossman");

    private final String displayName;

    BrandEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
