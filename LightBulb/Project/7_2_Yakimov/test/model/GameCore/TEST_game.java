package model.GameCore;

import model.Events.ExitCellEventListener;
import model.Events.GameEvent;
import model.Events.GameEventListener;
import model.GameCore.FinishingGameCore.SmartRobot;
import model.Navigation.Direction;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TEST_game {
    // ============ INIT PART ============
    // Events from exit cell
    private ArrayList<GameEvent> gameEvents = new ArrayList<>(); // list of gotten events from game

    // Exit cell event observer
    private class GameObserver implements GameEventListener {
        // ============= ATTRIBUTES =============
        private Field _field = null; // Field

        // ============= OPERATIONS =============
        // --------- contract ---------
        /** Make smart robot make a step in given direction
         * @param direction - direction in which smart robot will make a step
         */
        public void moveSmartRobot(@NotNull Direction direction) { this._field.getSmartRobot().makeStep(direction); }

        /** Check if smart robot was caught by somebody
         * @return sign of checking that smart robot was caught by somebody
         */
        public boolean isSmartRobotCaught() { return this._field.getSmartRobot().isCaught(); }

        /** Handle event when game was created
         * @param event - event information
         */
        public void gameWasCreated(GameEvent event) {
            gameEvents.add(event);
            this._field = event.getField();
        }

        /** Handle event when game's field is changed
         * @param event - event information
         */
        public void gameFieldIsChanged(GameEvent event) { gameEvents.add(event); }

        /** Handle event when game has defined that smart robot won
         * @param event - event information
         */
        public void smartRobotWon(GameEvent event) { gameEvents.add(event); }

        /** Handle event when game has defined that silly robot won
         * @param event - event information
         */
        public void sillyRobotWon(GameEvent event) { gameEvents.add(event); }
    }

    @BeforeEach
    void initEach() {
        gameEvents.clear();
    }

    // ============ TEST PART ============
    // --- TESTS SHORT DESCRIPTION ---
    // 1. Creation;
    // 1.1. Simple creation.
    // 1.1.1. Successful creation - simple creation.
    //
    // 2. Run.
    // 2.1. Game process;
    // 2.1.1. Init;
    // 2.1.2. Simple move.
    // 2.2. Somebody won;
    // 2.2.1. Smart robot has won;
    // 2.2.2. Silly robot has won.

    // ========== CREATION ==========
    // --- SIMPLE CREATION ---
    // Successful creation - SIMPLE creation
    @Test
    void basicConstructor_simpleCreation() {
        // Create initial state of entity
        GameObserver gameObserver = new GameObserver();

        Game game = new Game();
        game.addEventListener(gameObserver);

        // Check needed state of entity ...
        // ... check that game haven't fired any events
        Assertions.assertTrue(gameEvents.isEmpty());
    }

    // ============ RUN =============
    // --- GAME PROCESS ---
    // Game INIT
    @Test
    void run_gameInit() {
        // Create initial state of entity
        GameObserver gameObserver = new GameObserver();

        Game game = new Game();
        game.addEventListener(gameObserver);

        // Run testing operation
        game.run();

        // Check needed state of entity ...
        // ... check that game has fired one event (create event)
        Assertions.assertEquals(1, gameEvents.size());
    }

    // SIMPLE MOVE of smart robot
    @Test
    void run_simpleMoveOfSmartRobot() {
        // Create initial state of entity
        GameObserver gameObserver = new GameObserver();

        Game game = new Game();
        game.addEventListener(gameObserver);

        game.run();

        // Run testing operation
        gameObserver.moveSmartRobot(Direction.UP);

        // Check needed state of entity ...
        // ... check that game has fired two event (create event and field was updated event)
        Assertions.assertEquals(2, gameEvents.size());
        // ... check that smart robot wasn't caught by somebody
        Assertions.assertFalse(gameObserver.isSmartRobotCaught());
    }

    // --- SOMEBODY WON ---
    // SMART ROBOT HAS WON
    @Test
    void run_smartRobotHasWon() {
        // Create initial state of entity
        GameObserver gameObserver = new GameObserver();

        Game game = new Game();
        game.addEventListener(gameObserver);

        game.run();

        // Run testing operation
        gameObserver.moveSmartRobot(Direction.RIGHT);

        // Check needed state of entity ...
        // ... check that game has fired two event (create event and smart robot has won)
        Assertions.assertEquals(2, gameEvents.size());
        // ... check that smart robot was caught by somebody
        Assertions.assertTrue(gameObserver.isSmartRobotCaught());
    }

    // SILLY ROBOT HAS WON
    @Test
    void run_sillyRobotHasWon() {
        // Create initial state of entity
        GameObserver gameObserver = new GameObserver();

        Game game = new Game();
        game.addEventListener(gameObserver);

        game.run();

        // Run testing operation
        gameObserver.moveSmartRobot(Direction.LEFT);

        // Check needed state of entity ...
        // ... check that game has fired two event (create event and silly robot has won)
        Assertions.assertEquals(2, gameEvents.size());
        // ... check that smart robot was caught by somebody
        Assertions.assertTrue(gameObserver.isSmartRobotCaught());
    }
}
