CREATE TABLE driver(
    driver_id serial primary key,
    name VARCHAR(100) NOT NULL,
    age INT NOT null check (age > 0),
    HasDrivingLicense BOOLEAN NOT NULL
);

CREATE table car(
    car_id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (Price >= 0)
);

CREATE TABLE DriverCar (
    driver_id INT8 NOT NULL,
    car_id INT8 NOT NULL,
    PRIMARY KEY (driver_id, car_id),
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES car(car_id) ON DELETE CASCADE
);