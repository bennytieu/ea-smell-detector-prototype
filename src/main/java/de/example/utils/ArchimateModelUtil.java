package de.example.utils;
import java.util.Optional;

import de.example.model.LangStringType;
import de.example.model.ReferenceableType;

public class ArchimateModelUtil {

    public static String getName(ReferenceableType node) {
        return Optional.of(node)
                       .map(ReferenceableType::getNameGroup)
                       .map(nameGroup -> nameGroup.get(0))
                       .map(LangStringType::getValue)
                       .orElse(null);
    }
}
