package io.github.expansionteam.battleships.gui.models;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FieldBuilderTest {

    @Test
    public void shouldCreateEmptyOpponentField() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = Field.FieldBuilder.opponentField(positionMock).build();

        // then
        assertThat(field.isOccupied()).isFalse();
    }

    @Test
    public void shouldCreateEmptyPlayerField() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = Field.FieldBuilder.playerField(positionMock).build();

        // then
        assertThat(field.isOccupied()).isFalse();
    }

    @Test
    public void shouldCreateOccupiedOpponentField() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = Field.FieldBuilder.opponentField(positionMock).occupied().build();

        // then
        assertThat(field.isOccupied()).isTrue();
    }

    @Test
    public void shouldCreateOccupiedPlayerField() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = Field.FieldBuilder.playerField(positionMock).occupied().build();

        // then
        assertThat(field.isOccupied()).isTrue();
    }

    @Test
    public void shouldCreateOpponentFieldThatWasShot() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = Field.FieldBuilder.opponentField(positionMock).shot().build();

        // then
        assertThat(field.wasShot()).isTrue();
    }

    @Test
    public void shouldCreatePlayerFieldThatWasNotShot() {
        // given
        Position positionMock = mock(Position.class);

        // when
        Field field = Field.FieldBuilder.playerField(positionMock).build();

        // then
        assertThat(field.wasShot()).isFalse();
    }

}