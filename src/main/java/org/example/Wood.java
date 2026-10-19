package org.example;

public class Wood {
    private String Species;
    private String Type;
    private String Origin;
    private String Color;
    private Double AvailableStock;

    public Wood(String Species, String Type, String Origin, String Color, Double AvailableStock) throws LackingWoodDataException{
        if(Species==null||Species.isEmpty()){ throw new LackingWoodDataException("Species field invalid");}
        if(Type==null||Type.isEmpty()){ throw new LackingWoodDataException("Type field invalid");}
        if(Origin==null||Origin.isEmpty()){ throw new LackingWoodDataException("Origin field invalid");}
        if(Color==null||Color.isEmpty()){ throw new LackingWoodDataException("Color field invalid");}
        if(AvailableStock==null||AvailableStock<0){ throw new LackingWoodDataException("AvailableStock field invalid");}
        this.Species = Species;
        this.Type = Type;
        this.Origin = Origin;
        this.Color = Color;
        this.AvailableStock = AvailableStock;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Double getAvailableStock() {
        return AvailableStock;
    }

    public void setAvailableStock(Double availableStock) {
        AvailableStock = availableStock;
    }

    @Override
    public String toString(){
        return "Wood:\n=>Species = " + getSpecies() +
                "\n=>Type = " + getType() +
                "\n=>Origin = " + getOrigin() +
                "\n=>Color = " + getColor() +
                "\n=>Available Stock = " + getAvailableStock();
    }
}
