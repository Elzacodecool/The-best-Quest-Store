import com.codecool.model.Artifact;
import com.codecool.model.ArtifactDAO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)


public class ArtifactDAOTests {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private ResultSetMetaData metaData;


    @BeforeEach
    void setUpEnvironment() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void shouldAddArtifactThrowExceptionWhenNullPass() {
        ArtifactDAO artifactDAO1 = new ArtifactDAO(connection);

        assertThrows(IllegalArgumentException.class, () -> {
            artifactDAO1.add(null);
        });
    }

    @Test
    public void shouldUpdateArtifactThrowExceptionWhenNullPass() {
        ArtifactDAO artifactDAO1 = new ArtifactDAO(connection);

        assertThrows(IllegalArgumentException.class, () -> {
            artifactDAO1.update(null);
        });
    }

    @Test
    public void doesGetArtifactReturnProperArtifactDetails() throws SQLException {
        ArtifactDAO artifactDAO1 = new ArtifactDAO(connection);
        Artifact artifact1 = new Artifact(1, "name", "desc", 1, "cat");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(metaData.getColumnCount()).thenReturn(5);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("artifact_name");
        when(metaData.getColumnName(3)).thenReturn("artifact_description");
        when(metaData.getColumnName(4)).thenReturn("cost");
        when(metaData.getColumnName(5)).thenReturn("category");
        when(resultSet.getString("id")).thenReturn(Integer.toString(artifact1.getId()));
        when(resultSet.getString("artifact_name")).thenReturn(artifact1.getName());
        when(resultSet.getString("artifact_description")).thenReturn(artifact1.getDescription());
        when(resultSet.getString("cost")).thenReturn(Integer.toString(artifact1.getCost()));
        when(resultSet.getString("category")).thenReturn(artifact1.getCategory());

        ArrayList<String> methodDetails = getArtifactDetails(artifactDAO1.get(artifact1.getId()));
        ArrayList<String> expectedDetails = getArtifactDetails(artifact1);
        assertEquals(methodDetails, expectedDetails);
    }

    @Test
    public void doesGetListReturnProperListSize() throws SQLException {
        ArtifactDAO artifactDAO1 = new ArtifactDAO(connection);
        Artifact artifact1 = new Artifact(1, "name", "desc", 1, "cat");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(metaData.getColumnCount()).thenReturn(5);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("artifact_name");
        when(metaData.getColumnName(3)).thenReturn("artifact_description");
        when(metaData.getColumnName(4)).thenReturn("cost");
        when(metaData.getColumnName(5)).thenReturn("category");
        when(resultSet.getString("id")).thenReturn(Integer.toString(artifact1.getId()));
        when(resultSet.getString("artifact_name")).thenReturn(artifact1.getName());
        when(resultSet.getString("artifact_description")).thenReturn(artifact1.getDescription());
        when(resultSet.getString("cost")).thenReturn(Integer.toString(artifact1.getCost()));
        when(resultSet.getString("category")).thenReturn(artifact1.getCategory());

        List<Artifact> artifacts = artifactDAO1.getList();
        int expectedSize = 3;
        int actualSize = artifacts.size();
        assertEquals(expectedSize, actualSize);
    }

    private ArrayList<String> getArtifactDetails(Artifact artifact) {
        ArrayList<String> artifactDetails = new ArrayList<>();
        artifactDetails.add(artifact.getId().toString());
        artifactDetails.add(artifact.getName());
        artifactDetails.add(artifact.getDescription());
        artifactDetails.add(artifact.getCost().toString());
        artifactDetails.add(artifact.getCategory());

        return artifactDetails;
    }
}
