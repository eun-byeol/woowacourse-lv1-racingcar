package racingcar.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CarTest {

    private Car car;
    private static final String VALID_CAR_NAME = "조조는조조";
    private static final int MAX_OIL_AMOUNT = 9;

    @BeforeEach
    void initCar() {
        car = new Car(VALID_CAR_NAME);
    }

    @DisplayName("이름이 5자 초과이면 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"점심은순두부", "1234567"})
    void testInvalidNameLength(String carName) {
        assertThatThrownBy(() -> new Car(carName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름이 5자 이하이면 예외를 던지지 않는다")
    @ParameterizedTest
    @ValueSource(strings = {"점심순두부", "1"})
    void testValidNameLength(String carName) {
        assertDoesNotThrow(() -> new Car(carName));
    }

    @DisplayName("오일이 4 미만이면 이동하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void testDoNotGoIfOilNotEnough(int oil) {
        car.goIfOilEnough(oil);
        assertTrue(() -> car.isSameDistance(0));
    }

    @DisplayName("오일이 4 이상이면 이동한다")
    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6, 7, 8, 9})
    void testGoIfOilEnough(int oil) {
        car.goIfOilEnough(oil);
        assertTrue(() -> car.isSameDistance(1));
    }

    @DisplayName("가장 멀리 이동한 거리를 반환한다")
    @ParameterizedTest
    @MethodSource("provideCarsWithMaxDistance")
    void testFindMaxDistance(List<Car> cars, int maxDistance) {
        assertThat(Car.findMaxDistance(cars))
                .isEqualTo(maxDistance);
    }

    private static Stream<Arguments> provideCarsWithMaxDistance() {
        return Stream.of(
                Arguments.of(createCarsWithDistance(List.of(0, 1, 2, 3, 4, 5, 6)), 6),
                Arguments.of(createCarsWithDistance(List.of(10, 20, 50, 101, 100)), 101),
                Arguments.of(createCarsWithDistance(List.of(1, 1, 1, 1, 1, 1)), 1)
        );
    }

    private static List<Car> createCarsWithDistance(List<Integer> distances) {
        return distances.stream()
                .map(CarTest::createCarWithDistance)
                .toList();
    }

    private static Car createCarWithDistance(int distance) {
        Car car = new Car(VALID_CAR_NAME);
        while (distance-- > 0) {
            car.goIfOilEnough(MAX_OIL_AMOUNT);
        }
        return car;
    }
}