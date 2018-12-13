var App =  angular.module('App',[]);
App.controller('IndexController',['$scope',function ($scope) {
    $scope.current='1'; //初始化为第一个选中
    $scope.changePages=function (data) {
        if (data=='1'){
            $scope.current='1';
        }
        if (data=='2'){
            $scope.current='2';
        }
        if (data=='3'){
            $scope.current='3';
        }
        if (data=='4'){
            $scope.current='4';
        }
        if (data=='5'){
            $scope.current='5';
        }
    }
    $scope.showDetail = function (show) {
        show = true;
    }
    $scope.toLogin=function () {
        window.location.href="/login.html";
    }
}]);