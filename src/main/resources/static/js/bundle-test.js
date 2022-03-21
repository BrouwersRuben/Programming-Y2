/******/ (() => { // webpackBootstrap
var __webpack_exports__ = {};
/*!*****************************!*\
  !*** ./src/main/js/test.js ***!
  \*****************************/
var obj = { 'France': 'Paris', 'England': 'London' };

// Iterate over the property names:
for (let country of Object.keys(obj)) {
    var capital = obj[country];
    console.log(country, capital);
}

for (const [country, capital] of Object.entries(obj))
    console.log(country, capital);
/******/ })()
;
//# sourceMappingURL=bundle-test.js.map