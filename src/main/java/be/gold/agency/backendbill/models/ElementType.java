package be.gold.agency.backendbill.models;

public enum ElementType {
    JOUR("Jour"),
    DIVERS("Divers"),
    SERVICE("Service"),
    ARTICLE("Article");
    private final String displayName;

    ElementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}