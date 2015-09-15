'use strict';

angular.module('portfolioApp')
    .controller('GameOfLifeController', function () {
        var canvas = document.getElementById('game'); // for some reason angular.element doesn't work here
        var ctx = canvas.getContext("2d");
        ctx.fillStyle = "#FF0000";

        var width = 80;
        var height = 60;

        var size = 10;
        var cells = new Array(width);

        var initCells = function () {
            for (var i = 0; i < width; i++) {
                cells[i] = new Array(height);
            }
        };
        initCells();

        var fillCells = function () {
            for (var i = 0; i < width; i++) {
                for (var j = 0; j < height; j++) {
                    cells[i][j] = Math.random() >= 0.2;
                }
            }
        };
        fillCells();


        setInterval(function () {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            for (var i = 0; i < width; i++) {
                for (var j = 0; j < height; j++) {

                    var adjacent = getAdjacent(i, j);

                    if (cells[i][j]) {
                        // die from under-population or squeezed to death
                        if (adjacent < 2 || adjacent > 3) {
                            cells[i][j] = false;
                        }
                    } else {
                        if (adjacent == 3) {
                            cells[i][j] = true;
                        }
                    }

                    if (cells[i][j]) {
                        ctx.fillRect(i * size, j * size, size, size);
                    }
                }
            }
        }, 1000);

        var getAdjacent = function (x, y) {
            var adjacent = 0;

            // top
            if (y > 0 && cells[x][y - 1]) {
                adjacent++;
            }
            // bottom
            if (y < height - 1 && cells[x][y + 1]) {
                adjacent++;
            }
            // left
            if (x > 0 && cells[x - 1][y]) {
                adjacent++;
            }
            // right
            if (x < width - 1 && cells[x + 1][y]) {
                adjacent++;
            }
            // top left
            if (x > 0 && y > 0 && cells[x - 1][y - 1]) {
                adjacent++;
            }
            // top right
            if (x < width - 1 && y > 0 && cells[x + 1][y - 1]) {
                adjacent++;
            }
            // bottom left
            if (x > 0 && y < height - 1 && cells[x - 1][y + 1]) {
                adjacent++;
            }
            // bottom right
            if (x < width - 1 && y < height - 1 && cells[x + 1][y + 1]) {
                adjacent++;
            }
            return adjacent;
        };
    });
