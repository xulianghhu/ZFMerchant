'user strict';

//系统设置模块
var cs_repairModule = angular.module("cs_repairModule",[]);

var cs_repairController = function ($scope, $http, LoginService) {
	$("#leftRoute").show();
	//搜索
	$scope.submitSearch = function(){
		$scope.req={customer_id:80,search:$scope.search};
		$http.post("api/cs/repair/search", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result.content;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//筛选
	$scope.submitScreen = function(){
		$scope.req={customer_id:80,search:$scope.search,q:$scope.screen};
		$http.post("api/cs/repair/search", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result.content;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//订单列表
	$scope.orderlist = function () {
        $scope.req={customer_id:80};
        $http.post("api/cs/repair/getAll", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
                $scope.list = data.result.content;
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
    };
    //详情
    $scope.orderinfo=function (p) {
    	LoginService.poscd=p.id;
    	$scope.poscd=p.id;
    	window.location.href = '#/cs_repairinfo';
    };
    //取消
    $scope.cancelApply = function(o){
    	$scope.req={id:o.id};
		$http.post("api/cs/repair/cancelApply", $scope.req).success(function (data) {  //绑定
            if (data != null && data != undefined) {
            	$scope.orderlist();
            }
        }).error(function (data) {
            $("#serverErrorModal").modal({show: true});
        });
	};
	//重新提交
	$scope.resubmitCancel = function(o){
		$scope.req={id:o.id};
		$http.post("api/cs/repair/resubmitCancel", $scope.req).success(function (data) {  //绑定
			if (data != null && data != undefined) {
				$scope.orderlist();
			}
		}).error(function (data) {
			$("#serverErrorModal").modal({show: true});
		});
	};
    $scope.orderlist();
};

cs_repairModule.controller("cs_repairController", cs_repairController);
