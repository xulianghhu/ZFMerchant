'user strict';

//系统设置模块
var cs_leaseinfoModule = angular.module("cs_leaseinfoModule",[]);

var cs_leaseinfoController = function ($scope,$location, $http, LoginService) {
	$("#leftRoute").show();
	$scope.req={};
	$scope.req.id=$location.search()['infoId'];
    $scope.getInfo = function () {
    	$http.post("api/cs/lease/returns/getById", $scope.req).success(function (data) {  //绑定
            if (data.code==1) {
            	$scope.info=data.result;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //取消
    $scope.cancelApply = function(o){
    	$scope.req={id:o.id};
		$http.post("api/cs/lease/returns/cancelApply", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	$scope.getInfo();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//重新提交
	$scope.resubmitCancel = function(o){
		$scope.req={id:o.id};
		$http.post("api/cs/lease/returns/resubmitCancel", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.getInfo();
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
    $scope.getInfo();

};

cs_leaseinfoModule.controller("cs_leaseinfoController", cs_leaseinfoController);
