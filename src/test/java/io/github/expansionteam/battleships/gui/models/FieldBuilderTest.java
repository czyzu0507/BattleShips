package io.github.expansionteam.battleships.gui.models;

import javafx.geometry.Pos;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

public class FieldBuilderTest {

    @Test
    public void shouldCreateEmptyField() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = new Field.FieldBuilder(positionMock).build();

        // then
        assertThat(field.isOccupied()).isFalse();
    }

    @Test
    public void shouldCreateOccupiedField() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = new Field.FieldBuilder(positionMock).occupied().build();

        // then
        assertThat(field.isOccupied()).isTrue();
    }

    @Test
    public void shouldCreateFieldThatWasShot() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = new Field.FieldBuilder(positionMock).shot().build();

        // then
        assertThat(field.wasShot()).isTrue();
    }

    @Test
    public void shouldCreateFieldThatWasNotShot() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = new Field.FieldBuilder(positionMock).build();

        // then
        assertThat(field.wasShot()).isFalse();
    }
    
}