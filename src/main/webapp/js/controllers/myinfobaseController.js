'user strict';

// 系统设置模块
var myinfobaseModule = angular.module("myinfobaseModule", []);

var myinfobaseController = function($scope, $http, LoginService) {
	$scope.req = {};
	$scope.req.id = 8;
	$scope.init = function() {
		$http.get("api/customers/getOne/8", $scope.req).success(function(data) {
			if (data.code == 1) {
				$scope.customer = data.result;
			}
		}).error(function(data) {
			// $("#serverErrorModal").modal({
			// show : true
			// });
		});
	};
	$scope.save = function() {
		$scope.updateCustomer = {
			id : $scope.req.id,
			name : $scope.customer.name,
			phone : $scope.customer.phone,
			email : $scope.customer.email,
			cityId : $scope.customer.city_id
		};
		$http.post("api/customers/update", $scope.updateCustomer).success(function(data) {
			if (data.code == 1) {
				// 提示保存成功
			}
		}).error(function(data) {
			// $("#serverErrorModal").modal({
			// show : true
			// });
		});
	};
	$scope.init();
};
myinfobaseModule.controller("myinfobaseController", myinfobaseController);