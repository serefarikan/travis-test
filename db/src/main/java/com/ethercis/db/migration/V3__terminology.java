package com.ethercis.db.migration;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This migration reads in the terminology.xml file and stores its contents into the database.
 *
 * This replaces the com.ethercis.dao.access.support.TerminologySetter class
 */
public class V3__terminology implements JdbcMigration {

    @Override
    public void migrate(final Connection connection) throws Exception {
        try (InputStream resourceAsStream = getClass().getClassLoader()
                .getResourceAsStream("terminology.xml")) {

            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            final Document document = documentBuilder.parse(resourceAsStream);

            setTerritory(connection, document);
            setLanguage(connection, document);
            setConcept(connection, document);
        }
    }

    private void setTerritory(final Connection connection, final Document document)
            throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO ehr.territory(code, twoletter, threeletter, text) VALUES (?, ?, ?, ?)")) {
            final NodeList territory = document.getElementsByTagName("Territory");
            for (int idx = 0; idx < territory.getLength(); idx++) {
                final Node item = territory.item(idx);
                final NamedNodeMap attributes = item.getAttributes();

                final Integer code = Integer
                        .valueOf(attributes.getNamedItem("NumericCode").getNodeValue());

                final String two = attributes.getNamedItem("TwoLetter").getNodeValue();
                final String three = attributes.getNamedItem("ThreeLetter").getNodeValue();
                final String text = attributes.getNamedItem("Text").getNodeValue();

                statement.setInt(1, code);
                statement.setString(2, two);
                statement.setString(3, three);
                statement.setString(4, text);
                statement.executeUpdate();
            }
        }
    }

    private void setLanguage(final Connection connection, final Document document)
            throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO ehr.language(code, description) VALUES (?, ?)")) {
            final NodeList language = document.getElementsByTagName("Language");
            for (int idx = 0; idx < language.getLength(); idx++) {
                final Node item = language.item(idx);
                final NamedNodeMap attributes = item.getAttributes();

                final String code = attributes.getNamedItem("code").getNodeValue();
                final String text = attributes.getNamedItem("Description").getNodeValue();

                statement.setString(1, code);
                statement.setString(2, text);
                statement.executeUpdate();
            }
        }
    }

    private void setConcept(final Connection connection, final Document document)
            throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO ehr.concept(conceptId, language, description) VALUES (?, ?, ?)")) {
            final NodeList concept = document.getElementsByTagName("Concept");
            for (int idx = 0; idx < concept.getLength(); idx++) {
                final Node item = concept.item(idx);
                final NamedNodeMap attributes = item.getAttributes();

                final Integer code = Integer
                        .valueOf(attributes.getNamedItem("ConceptID").getNodeValue());

                final String language = attributes.getNamedItem("Language").getNodeValue();
                final String text = attributes.getNamedItem("Rubric").getNodeValue();

                statement.setInt(1, code);
                statement.setString(2, language);
                statement.setString(3, text);
                statement.executeUpdate();
            }
        }
    }

}