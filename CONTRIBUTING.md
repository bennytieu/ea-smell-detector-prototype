Using JAXB, you would:

1. Bind the schema for the XML document.
2. Unmarshal the document into Java content objects. The Java content objects represent the content and organization of the XML document, and are directly available to your program.

For 1. Binding \
``xjc.sh -p de.example.model archimate3_model.xsd -d src``

If needed compile with \
``javac example/model/*.java``

For 2. Unmarshalling \
use the program
