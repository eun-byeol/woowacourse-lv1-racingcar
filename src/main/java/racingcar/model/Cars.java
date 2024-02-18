package racingcar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

public class Cars {

    private final List<Car> cars;

    public Cars(List<Car> cars) {
        this.cars = new ArrayList<>(cars);
    }

    public void moveAllCars() {
        cars.forEach(Car::move);
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public List<Car> determineWinners() {
        int farthestDistance = getFarthestDistance();

        return cars.stream().filter(car -> car.getPosition() == farthestDistance).collect(Collectors.toList());
    }

    private int getFarthestDistance() {
        return cars.stream().mapToInt(Car::getPosition).max().orElse(0);
    }

}
