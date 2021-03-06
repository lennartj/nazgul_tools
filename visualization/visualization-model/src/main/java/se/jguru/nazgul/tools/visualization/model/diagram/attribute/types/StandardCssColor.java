/*-
 * #%L
 * Nazgul Project: nazgul-tools-visualization-model
 * %%
 * Copyright (C) 2010 - 2018 jGuru Europe AB
 * %%
 * Licensed under the jGuru Europe AB license (the "License"), based
 * on Apache License, Version 2.0; you may not use this file except
 * in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *       http://www.jguru.se/licenses/jguruCorporateSourceLicense-2.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


package se.jguru.nazgul.tools.visualization.model.diagram.attribute.types;

import se.jguru.nazgul.tools.visualization.model.diagram.AbstractIdentifiable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * An enumeration containing the 140 colors commonly supported in all CSS and Browsers,
 * as indicated on the Java2S website. (C.f.
 * <a href="http://www.java2s.com/Tutorials/HTML_CSS/Reference/Encoding_Reference/List_of_Color_Names_Supported_by_All_Browsers.htm">
 * The HTML_CSS Tutorials</a>).
 *
 * @author <a href="mailto:lj@jguru.se">Lennart J&ouml;relid</a>, jGuru Europe AB
 */
@XmlType(namespace = AbstractIdentifiable.NAMESPACE)
@XmlEnum(String.class)
public enum StandardCssColor implements Serializable, TokenValueHolder {

    AliceBlue("F0F8FF"),
    AntiqueWhite("FAEBD7"),
    Aqua("00FFFF"),
    Aquamarine("7FFFD4"),
    Azure("F0FFFF"),
    Beige("F5F5DC"),
    Bisque("FFE4C4"),
    Black("000000"),
    BlanchedAlmond("FFEBCD"),
    Blue("0000FF"),
    BlueViolet("8A2BE2"),
    Brown("A52A2A"),
    BurlyWood("DEB887"),
    CadetBlue("5F9EA0"),
    Chartreuse("7FFF00"),
    Chocolate("D2691E"),
    Coral("FF7F50"),
    CornflowerBlue("6495ED"),
    Cornsilk("FFF8DC"),
    Crimson("DC143C"),
    Cyan("00FFFF"),
    DarkBlue("00008B"),
    DarkCyan("008B8B"),
    DarkGoldenRod("B8860B"),
    DarkGrey("A9A9A9"),
    DarkGreen("006400"),
    DarkKhaki("BDB76B"),
    DarkMagenta("8B008B"),
    DarkOliveGreen("556B2F"),
    Darkorange("FF8C00"),
    DarkOrchid("9932CC"),
    DarkRed("8B0000"),
    DarkSalmon("E9967A"),
    DarkSeaGreen("8FBC8F"),
    DarkSlateBlue("483D8B"),
    DarkSlateGrey("2F4F4F"),
    DarkTurquoise("00CED1"),
    DarkViolet("9400D3"),
    DeepPink("FF1493"),
    DeepSkyBlue("00BFFF"),
    DimGray("696969"),
    DodgerBlue("1E90FF"),
    FireBrick("B22222"),
    FloralWhite("FFFAF0"),
    ForestGreen("228B22"),
    Fuchsia("FF00FF"),
    Gainsboro("DCDCDC"),
    GhostWhite("F8F8FF"),
    Gold("FFD700"),
    GoldenRod("DAA520"),
    Grey("808080"),
    Green("008000"),
    GreenYellow("ADFF2F"),
    HoneyDew("F0FFF0"),
    HotPink("FF69B4"),
    IndianRed("CD5C5C"),
    Indigo("4B0082"),
    Ivory("FFFFF0"),
    Khaki("F0E68C"),
    Lavender("E6E6FA"),
    LavenderBlush("FFF0F5"),
    LawnGreen("7CFC00"),
    LemonChiffon("FFFACD"),
    LightBlue("ADD8E6"),
    LightCoral("F08080"),
    LightCyan("E0FFFF"),
    LightGoldenRodYellow("FAFAD2"),
    LightGrey("D3D3D3"),
    LightGreen("90EE90"),
    LightPink("FFB6C1"),
    LightSalmon("FFA07A"),
    LightSeaGreen("20B2AA"),
    LightSkyBlue("87CEFA"),
    LightSlateGrey("778899"),
    LightSteelBlue("B0C4DE"),
    LightYellow("FFFFE0"),
    Lime("00FF00"),
    LimeGreen("32CD32"),
    Linen("FAF0E6"),
    Magenta("FF00FF"),
    Maroon("800000"),
    MediumAquaMarine("66CDAA"),
    MediumBlue("0000CD"),
    MediumOrchid("BA55D3"),
    MediumPurple("9370D8"),
    MediumSeaGreen("3CB371"),
    MediumSlateBlue("7B68EE"),
    MediumSpringGreen("00FA9A"),
    MediumTurquoise("48D1CC"),
    MediumVioletRed("C71585"),
    MidnightBlue("191970"),
    MintCream("F5FFFA"),
    MistyRose("FFE4E1"),
    Moccasin("FFE4B5"),
    NavajoWhite("FFDEAD"),
    Navy("000080"),
    OldLace("FDF5E6"),
    Olive("808000"),
    OliveDrab("6B8E23"),
    Orange("FFA500"),
    OrangeRed("FF4500"),
    Orchid("DA70D6"),
    PaleGoldenRod("EEE8AA"),
    PaleGreen("98FB98"),
    PaleTurquoise("AFEEEE"),
    PaleVioletRed("D87093"),
    PapayaWhip("FFEFD5"),
    PeachPuff("FFDAB9"),
    Peru("CD853F"),
    Pink("FFC0CB"),
    Plum("DDA0DD"),
    PowderBlue("B0E0E6"),
    Purple("800080"),
    Red("FF0000"),
    RosyBrown("BC8F8F"),
    RoyalBlue("4169E1"),
    SaddleBrown("8B4513"),
    Salmon("FA8072"),
    SandyBrown("F4A460"),
    SeaGreen("2E8B57"),
    SeaShell("FFF5EE"),
    Sienna("A0522D"),
    Silver("C0C0C0"),
    SkyBlue("87CEEB"),
    SlateBlue("6A5ACD"),
    SlateGrey("708090"),
    Snow("FFFAFA"),
    SpringGreen("00FF7F"),
    SteelBlue("4682B4"),
    Tan("D2B48C"),
    Teal("008080"),
    Thistle("D8BFD8"),
    Tomato("FF6347"),
    Turquoise("40E0D0"),
    Violet("EE82EE"),
    Wheat("F5DEB3"),
    White("FFFFFF"),
    WhiteSmoke("F5F5F5"),
    Yellow("FFFF00"),
    YellowGreen("9ACD32");

    // Internal state
    private String rgbCode;

    /**
     * Creates a StandardCssColor wrapping the supplied (hexadecimal) RGB value.
     *
     * @param rgbCode A hexadecimal RGB value.
     */
    StandardCssColor(final String rgbCode) {
        this.rgbCode = rgbCode;
    }

    /**
     * Retrieves the hexadecimal RGB value corresponding to this StandardCssColor.
     *
     * @return the hexadecimal RGB value corresponding to this
     * StandardCssColor, on the form {@code #RRGGBB}.
     */
    public String getRgbValue() {
        return "#" + rgbCode;
    }

    /**
     * Finds the {@link StandardCssColor} whose constant/name() or RGB value matches the supplied argument.
     *
     * @param nameOrRgbValue A non-empty String either matching the {@link StandardCssColor#name()} in a case
     *                       insensitive search, or being equivalent to the {@link #getRgbValue()} of a
     *                       {@link StandardCssColor}.
     * @return the {@link StandardCssColor} whose constant/name() or RGB value matches the supplied argument, or
     * {@code null} if no {@link StandardCssColor} matched the supplied argument.
     */
    public static StandardCssColor convert(final String nameOrRgbValue) {

        StandardCssColor toReturn = null;

        if (nameOrRgbValue != null && !nameOrRgbValue.isEmpty()) {

            final String effective = nameOrRgbValue.trim().toUpperCase();
            for (StandardCssColor current : StandardCssColor.values()) {

                // #1) Try a case-insensitive match for the name().
                if (current.name().equalsIgnoreCase(effective)) {
                    toReturn = current;
                    break;
                }

                // #2) Try matching the RGB value.
                if (current.getRgbValue().endsWith(effective)) {
                    toReturn = current;
                    break;
                }
            }
        }

        // All Done.
        return toReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTokenValue() {
        return getRgbValue();
    }
}
