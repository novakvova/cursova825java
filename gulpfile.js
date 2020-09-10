"use strict";

var gulp = require('gulp'),
    rimraf = require("rimraf"),
    concat = require("gulp-concat"),
    cssmin = require("gulp-cssmin"),
    uglify = require("gulp-uglify");
var merge = require('merge-stream');

//var paths = {
//    webroot: "./wwwroot/"
//};

var depscss = {
    "bootstrap": {
        "dist/css/bootstrap.min.css": ""
    },
    "@fortawesome": {
        "fontawesome-free/css/all.css": "",
        "fontawesome-free/css/fontawesome.css": "",
    },
    "chart.js": {
        "dist/Chart.css": "",
        "dist/Chart.min.css": "",
    },
    "cropperjs": {
        "dist/cropper.css": ""
    },
    "vuetify": {
        "dist/vuetify.css": "/vue/"
    }
};

var depsjs = {
    "bootstrap": {
        "dist/js/bootstrap.min.js": ""
    },
    "jquery": {
        "dist/jquery.js": ""
    },
    "popper.js": {
        "dist/popper.js": ""
    },
    "@fortawesome": {
        "fontawesome-free/js/all.js": "",
        "fontawesome-free/js/fontawesome.js": "",
    },
    "chart.js": {
        "dist/Chart.js": "",
        "dist/Chart.min.js": ""
    },
    "cropperjs": {
        "dist/cropper.js": ""
    },
    "vue": {
        "dist/vue.min.js": "vue/"
    },
    "vue-router": {
        "dist/vue-router.min.js": "vue/"
    },
    "vuetify": {
        "dist/vuetify.js": "vue/"
    },
    // "axios": {
    //     "dist/axios.min.js": ""
    // }
};

gulp.task("clean", function (cb) {
    return rimraf("src/main/resources/static/lib", cb);
});

gulp.task("js", function () {
    var streams = [];

    for (var prop in depsjs) {
        console.log("Hello gulp depsjs: " + prop);
        for (var itemProp in depsjs[prop]) {
            streams.push(gulp.src("node_modules/" + prop + "/" + itemProp)
                .pipe(gulp.dest("src/main/resources/static/lib/js/" + depsjs[prop][itemProp])));
        }
    }
    return merge(streams);
});

gulp.task("css", function () {
    var streams = [];

    for (var prop in depscss) {
        console.log("Hello gulp depscss: " + prop);
        for (var itemProp in depscss[prop]) {
            streams.push(gulp.src("node_modules/" + prop + "/" + itemProp)
                .pipe(gulp.dest("src/main/resources/static/lib/css" + depscss[prop][itemProp])));
        }
    }
    return merge(streams);
});

gulp.task("scripts", gulp.series(["js", "css"]));