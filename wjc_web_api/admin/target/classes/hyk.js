(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["chunk-4c6a5fbe"], {
    "0fec": function (t, e, a) {
    }, "1ac3": function (t, e, a) {
    }, "364d": function (t, e, a) {
        "use strict";
        var n = a("68c7"), s = a.n(n);
        s.a
    }, "46da": function (t, e, a) {
    }, 5682: function (t, e, a) {
    }, "580a": function (t, e, a) {
    }, "66c7": function (t, e, a) {
        "use strict";
        var n = a("46da"), s = a.n(n);
        s.a
    }, "68c7": function (t, e, a) {
    }, 9406: function (t, e, a) {
        "use strict";
        a.r(e);
        var n = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("div", {staticClass: "dashboard-container"}, [a(t.currentRole, {tag: "component"})], 1)
            }, s = [], i = (a("2338"), a("f763"), a("fb37"), a("a506")), r = a("52c1"), c = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("div", {staticClass: "dashboard-editor-container"}, [a("panel-group", {
                    attrs: {countData: t.count},
                    on: {handleSetLineChartData: t.handleSetLineChartData}
                }), t._v(" "), a("el-row", {attrs: {gutter: 8}}, [a("el-col", {
                    staticStyle: {
                        "padding-right": "8px",
                        "margin-bottom": "30px"
                    }, attrs: {xs: {span: 24}, sm: {span: 24}, md: {span: 24}, lg: {span: 12}, xl: {span: 12}}
                }, [a("statistics", {attrs: {countData: t.count}}), t._v(" "), a("div", {staticClass: "chart-wrapper"}, [a("pie-chart", {on: {count: t.getCountData}})], 1)], 1), t._v(" "), a("el-col", {
                    staticStyle: {"margin-bottom": "30px"},
                    attrs: {xs: {span: 24}, sm: {span: 24}, md: {span: 24}, lg: {span: 12}, xl: {span: 12}}
                }, [a("activity", {attrs: {countData: t.count}})], 1)], 1)], 1)
            }, o = [], l = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("a", {
                    staticClass: "github-corner",
                    attrs: {
                        href: "https://github.com/PanJiaChen/vue-element-admin",
                        target: "_blank",
                        "aria-label": "View source on Github"
                    }
                }, [a("svg", {
                    staticStyle: {fill: "#40c9c6", color: "#fff"},
                    attrs: {width: "80", height: "80", viewBox: "0 0 250 250", "aria-hidden": "true"}
                }, [a("path", {attrs: {d: "M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"}}), t._v(" "), a("path", {
                    staticClass: "octo-arm",
                    staticStyle: {"transform-origin": "130px 106px"},
                    attrs: {
                        d: "M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2",
                        fill: "currentColor"
                    }
                }), t._v(" "), a("path", {
                    staticClass: "octo-body",
                    attrs: {
                        d: "M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z",
                        fill: "currentColor"
                    }
                })])])
            }, u = [], d = (a("364d"), a("6691")), p = {}, h = Object(d["a"])(p, l, u, !1, null, "4c6d8d88", null),
            f = h.exports, v = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("el-row", {
                    staticClass: "panel-group",
                    attrs: {gutter: 40}
                }, [a("el-col", {
                    staticClass: "card-panel-col",
                    attrs: {xs: 12, sm: 12, lg: 6}
                }, [a("div", {staticClass: "card-panel"}, [a("div", {staticClass: "card-panel-icon-wrapper icon-people"}, [a("svg-icon", {
                    attrs: {
                        "icon-class": "peoples",
                        "class-name": "card-panel-icon"
                    }
                })], 1), t._v(" "), a("div", {
                    staticClass: "card-panel-description",
                    on: {click: t.visitorRegister}
                }, [a("div", {staticClass: "card-panel-text"}, [t._v("\n          总床位数\n        ")]), t._v(" "), a("span", {staticClass: "card-panel-num"}, [t._v(t._s(t.countData.totalBedCount))])])])]), t._v(" "), a("el-col", {
                    staticClass: "card-panel-col",
                    attrs: {xs: 12, sm: 12, lg: 6}
                }, [a("div", {
                    staticClass: "card-panel",
                    on: {click: t.toClient}
                }, [a("div", {staticClass: "card-panel-icon-wrapper icon-message"}, [a("svg-icon", {
                    attrs: {
                        "icon-class": "message",
                        "class-name": "card-panel-icon"
                    }
                })], 1), t._v(" "), a("div", {staticClass: "card-panel-description"}, [a("div", {staticClass: "card-panel-text"}, [t._v("\n          开放床位数\n        ")]), t._v(" "), a("span", {staticClass: "card-panel-num"}, [t._v(t._s(t.countData.totalOnLineBed))])])])]), t._v(" "), a("el-col", {
                    staticClass: "card-panel-col",
                    attrs: {xs: 12, sm: 12, lg: 6}
                }, [a("div", {
                    staticClass: "card-panel",
                    on: {click: t.toTryLive}
                }, [a("div", {staticClass: "card-panel-icon-wrapper icon-money"}, [a("svg-icon", {
                    attrs: {
                        "icon-class": "money",
                        "class-name": "card-panel-icon"
                    }
                })], 1), t._v(" "), a("div", {staticClass: "card-panel-description"}, [a("div", {staticClass: "card-panel-text"}, [t._v("\n          入住总人数\n        ")]), t._v(" "), a("span", {staticClass: "card-panel-num"}, [t._v(t._s(t.countData.checkInCount))])])])]), t._v(" "), a("el-col", {
                    staticClass: "card-panel-col",
                    attrs: {xs: 12, sm: 12, lg: 6}
                }, [a("div", {
                    staticClass: "card-panel",
                    on: {click: t.toComeLive}
                }, [a("div", {staticClass: "card-panel-icon-wrapper icon-shopping"}, [a("svg-icon", {
                    attrs: {
                        "icon-class": "shopping",
                        "class-name": "card-panel-icon"
                    }
                })], 1), t._v(" "), a("div", {staticClass: "card-panel-description"}, [a("div", {staticClass: "card-panel-text"}, [t._v("\n          退住总人数\n        ")]), t._v(" "), a("span", {staticClass: "card-panel-num"}, [t._v(t._s(t.countData.checkoutCount))])])])])], 1)
            }, m = [], b = {
                props: {countData: {type: Object}}, methods: {
                    visitorRegister: function () {
                    }, toClient: function () {
                        this.$router.push("/org/client")
                    }, toTryLive: function () {
                        this.$router.push("/org/general")
                    }, toComeLive: function () {
                        this.$router.push("/org/come-live")
                    }
                }
            }, _ = b, C = (a("b154"), Object(d["a"])(_, v, m, !1, null, "1ad9d43a", null)), g = C.exports, y = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("div", {class: t.className, style: {height: t.height, width: t.width}})
            }, w = [], O = a("24ce"), D = a.n(O), x = a("ed08"), $ = {
                data: function () {
                    return {$_sidebarElm: null}
                }, mounted: function () {
                    this.$_initResizeEvent(), this.$_initSidebarResizeEvent()
                }, beforeDestroy: function () {
                    this.$_destroyResizeEvent(), this.$_destroySidebarResizeEvent()
                }, activated: function () {
                    this.$_initResizeEvent(), this.$_initSidebarResizeEvent()
                }, deactivated: function () {
                    this.$_destroyResizeEvent(), this.$_destroySidebarResizeEvent()
                }, methods: {
                    $_resizeHandler: function () {
                        var t = this;
                        return Object(x["b"])(function () {
                            t.chart && t.chart.resize()
                        }, 100)()
                    }, $_initResizeEvent: function () {
                        window.addEventListener("resize", this.$_resizeHandler)
                    }, $_destroyResizeEvent: function () {
                        window.removeEventListener("resize", this.$_resizeHandler)
                    }, $_sidebarResizeHandler: function (t) {
                        "width" === t.propertyName && this.$_resizeHandler()
                    }, $_initSidebarResizeEvent: function () {
                        this.$_sidebarElm = document.getElementsByClassName("sidebar-container")[0], this.$_sidebarElm && this.$_sidebarElm.addEventListener("transitionend", this.$_sidebarResizeHandler)
                    }, $_destroySidebarResizeEvent: function () {
                        this.$_sidebarElm && this.$_sidebarElm.removeEventListener("transitionend", this.$_sidebarResizeHandler)
                    }
                }
            }, j = a("6797"), E = a("5f87");
        a("6cbb");
        var S = {
                mixins: [$],
                props: {
                    className: {type: String, default: "chart"},
                    width: {type: String, default: "100%"},
                    height: {type: String, default: "440px"}
                },
                data: function () {
                    return {chart: null, count: {}}
                },
                mounted: function () {
                    this.queryCompanySum(), this.$nextTick(function () {
                    })
                },
                beforeDestroy: function () {
                    this.chart && (this.chart.dispose(), this.chart = null)
                },
                methods: {
                    queryCompanySum: function () {
                        var t = this, e = {companyId: Object(E["a"])()};
                        Object(j["t"])(e).then(function (e) {
                            e && 0 === e.data.resCode && (t.count = e.data.resBody, t.$emit("count", t.count), t.initChart())
                        })
                    }, initChart: function () {
                        this.chart = D.a.init(this.$el, "macarons");
                        var t = this;
                        this.chart.setOption({
                            tooltip: {trigger: "item", formatter: "{a} <br/>{b} : {c} ({d}%)"},
                            legend: {left: "center", bottom: 20, data: ["试住人数", "排队人数", "入住办理人数", "入住人数"]},
                            series: [{
                                name: "院内统计",
                                type: "pie",
                                radius: "75%",
                                center: ["50%", "50%"],
                                data: [{value: t.count.tryliveCount, name: "试住人数"}, {
                                    value: t.count.queueCount,
                                    name: "排队人数"
                                }, {value: t.count.checkCount, name: "入住办理人数"}, {
                                    value: t.count.checkInCount,
                                    name: "入住人数"
                                }],
                                itemStyle: {emphasis: {shadowBlur: 10, shadowOffsetX: 0, shadowColor: "rgba(0, 0, 0, 0.5)"}}
                            }]
                        })
                    }
                }
            }, z = S, k = Object(d["a"])(z, y, w, !1, null, null, null), P = k.exports, L = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("el-table", {
                    staticStyle: {width: "100%", "padding-top": "15px"},
                    attrs: {data: t.list}
                }, [a("el-table-column", {
                    attrs: {
                        label: "标题",
                        prop: "title",
                        "min-width": "200"
                    }
                }), t._v(" "), a("el-table-column", {
                    attrs: {
                        label: "日期",
                        prop: "date",
                        width: "195",
                        align: "center"
                    }
                })], 1)
            }, R = [], I = {
                data: function () {
                    return {
                        list: [{
                            title: "从老年综合评估到机构质量管控，老年人照护细节一次性解读",
                            date: "2019-05-20"
                        }, {
                            title: "探索医养融合新模式：法国欧葆庭养老集团与江苏省人民医院签订合作谅解备忘录",
                            date: "2019-03-01"
                        }, {title: "法式养老院年度餐饮计划曝光，南京长沙地方菜系大PK", date: "2019-02-28"}, {
                            title: "养老院持续10天的音乐盛会是怎样一种场面",
                            date: "2019-02-23"
                        }, {title: "高龄老年人生命意义何在？", date: "2019-01-12"}]
                    }
                }, created: function () {
                    Object(j["g"])().then(function (t) {
                    })
                }, methods: {}
            }, M = I, G = Object(d["a"])(M, L, R, !1, null, null, null), H = G.exports, T = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("el-card", {staticClass: "box-card"}, [a("div", {
                    staticClass: "clearfix",
                    attrs: {slot: "header"},
                    slot: "header"
                }, [a("span", [t._v("院内统计")])]), t._v(" "), a("el-form", {
                    ref: "form",
                    attrs: {"label-width": "100px"}
                }, [a("el-row", [a("el-col", {attrs: {span: 12}}, [a("el-form-item", {attrs: {label: "试住人数"}}, [a("span", [t._v(t._s(t.countData.tryliveCount))])])], 1), t._v(" "), a("el-col", {attrs: {span: 12}}, [a("el-form-item", {attrs: {label: "排队人数"}}, [a("span", [t._v(t._s(t.countData.queueCount))])])], 1)], 1), t._v(" "), a("el-row", [a("el-col", {attrs: {span: 12}}, [a("el-form-item", {attrs: {label: "入住办理人数"}}, [a("span", [t._v(t._s(t.countData.checkCount))])])], 1), t._v(" "), a("el-col", {attrs: {span: 12}}, [a("el-form-item", {attrs: {label: "入住人数"}}, [a("span", [t._v(t._s(t.countData.checkInCount))])])], 1)], 1)], 1)], 1)
            }, B = [], q = {
                name: "Statistics", props: {countData: {type: Object}}, data: function () {
                    return {}
                }
            }, N = q, J = (a("ab27"), Object(d["a"])(N, T, B, !1, null, "fa8e86b8", null)), V = J.exports, A = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("el-card", {staticClass: "box-card"}, [a("div", {
                    staticClass: "clearfix",
                    attrs: {slot: "header"},
                    slot: "header"
                }, [a("span", [t._v("本月活动")])]), t._v(" "), a("el-form", {
                    ref: "form",
                    attrs: {"label-width": "100px"}
                }, [a("el-row", [a("el-col", {attrs: {span: 8}}, [a("el-form-item", {attrs: {label: "登记人数"}}, [a("span", [t._v(t._s(t.countData.registerMonthCount))])])], 1), t._v(" "), a("el-col", {attrs: {span: 8}}, [a("el-form-item", {attrs: {label: "入住人数"}}, [a("span", [t._v(t._s(t.countData.checkInMonthCount))])])], 1), t._v(" "), a("el-col", {attrs: {span: 8}}, [a("el-form-item", {attrs: {label: "占比"}}, [a("span", [t._v(t._s(0 !== t.countData.checkInMonthCount && 0 !== t.countData.registerMonthCount ? (t.countData.checkInMonthCount / t.countData.registerMonthCount * 100).toFixed(2) : 0) + "%")])])], 1)], 1), t._v(" "), a("el-row", [a("el-col", {attrs: {span: 24}}, [a("el-form-item", {attrs: {label: "退住人数"}}, [a("span", [t._v(t._s(t.countData.checkOutMonthCount))])])], 1)], 1)], 1)], 1)
            }, Z = [], F = {name: "Statistics", props: {countData: {type: Object}}}, K = F,
            X = (a("f1f8"), Object(d["a"])(K, A, Z, !1, null, "a1315ad4", null)), Y = X.exports, Q = {
                newVisitis: {
                    expectedData: [100, 120, 161, 134, 105, 160, 165],
                    actualData: [120, 82, 91, 154, 162, 140, 145]
                },
                messages: {
                    expectedData: [200, 192, 120, 144, 160, 130, 140],
                    actualData: [180, 160, 151, 106, 145, 150, 130]
                },
                purchases: {
                    expectedData: [80, 100, 121, 104, 105, 90, 100],
                    actualData: [120, 90, 100, 138, 142, 130, 130]
                },
                shoppings: {
                    expectedData: [130, 140, 141, 142, 145, 150, 160],
                    actualData: [120, 82, 91, 154, 162, 140, 130]
                }
            }, U = {
                name: "DashboardAdmin",
                components: {GithubCorner: f, PanelGroup: g, PieChart: P, TransactionTable: H, Statistics: V, Activity: Y},
                data: function () {
                    return {lineChartData: Q.newVisitis, count: {}}
                },
                methods: {
                    handleSetLineChartData: function (t) {
                        this.lineChartData = Q[t]
                    }, getCountData: function (t) {
                        this.count = t
                    }
                }
            }, W = U, tt = (a("66c7"), Object(d["a"])(W, c, o, !1, null, "74c4634f", null)), et = tt.exports,
            at = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("div", {staticClass: "dashboard-editor-container"}, [a("div", {staticClass: " clearfix"}, [a("pan-thumb", {
                    staticStyle: {float: "left"},
                    attrs: {image: t.avatar}
                }, [t._v("\n      Your roles:\n      "), t._l(t.roles, function (e) {
                    return a("span", {key: e, staticClass: "pan-info-roles"}, [t._v(t._s(e))])
                })], 2), t._v(" "), a("github-corner", {
                    staticStyle: {
                        position: "absolute",
                        top: "0px",
                        border: "0",
                        right: "0"
                    }
                }), t._v(" "), a("div", {staticClass: "info-container"}, [a("span", {staticClass: "display_name"}, [t._v(t._s(t.name))]), t._v(" "), a("span", {
                    staticStyle: {
                        "font-size": "20px",
                        "padding-top": "20px",
                        display: "inline-block"
                    }
                }, [t._v("Editor's Dashboard")])])], 1), t._v(" "), a("div", [a("img", {
                    staticClass: "emptyGif",
                    attrs: {src: t.emptyGif}
                })])])
            }, nt = [], st = function () {
                var t = this, e = t.$createElement, a = t._self._c || e;
                return a("div", {
                    staticClass: "pan-item",
                    style: {zIndex: t.zIndex, height: t.height, width: t.width}
                }, [a("div", {staticClass: "pan-info"}, [a("div", {staticClass: "pan-info-roles-container"}, [t._t("default")], 2)]), t._v(" "), a("div", {
                    staticClass: "pan-thumb",
                    style: {backgroundImage: "url(" + t.image + ")"}
                })])
            }, it = [], rt = (a("d4d5"), {
                name: "PanThumb",
                props: {
                    image: {type: String, required: !0},
                    zIndex: {type: Number, default: 1},
                    width: {type: String, default: "150px"},
                    height: {type: String, default: "150px"}
                }
            }), ct = rt, ot = (a("f86f"), Object(d["a"])(ct, st, it, !1, null, "72e02616", null)), lt = ot.exports;

        function ut(t, e) {
            var a = Object.keys(t);
            if (Object.getOwnPropertySymbols) {
                var n = Object.getOwnPropertySymbols(t);
                e && (n = n.filter(function (e) {
                    return Object.getOwnPropertyDescriptor(t, e).enumerable
                })), a.push.apply(a, n)
            }
            return a
        }

        function dt(t) {
            for (var e = 1; e < arguments.length; e++) {
                var a = null != arguments[e] ? arguments[e] : {};
                e % 2 ? ut(a, !0).forEach(function (e) {
                    Object(i["a"])(t, e, a[e])
                }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(t, Object.getOwnPropertyDescriptors(a)) : ut(a).forEach(function (e) {
                    Object.defineProperty(t, e, Object.getOwnPropertyDescriptor(a, e))
                })
            }
            return t
        }

        var pt = {
            name: "DashboardEditor", components: {PanThumb: lt, GithubCorner: f}, data: function () {
                return {emptyGif: "https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3"}
            }, computed: dt({}, Object(r["b"])(["name", "avatar", "roles"]))
        }, ht = pt, ft = (a("efff"), Object(d["a"])(ht, at, nt, !1, null, "9c953d6a", null)), vt = ft.exports;

        function mt(t, e) {
            var a = Object.keys(t);
            if (Object.getOwnPropertySymbols) {
                var n = Object.getOwnPropertySymbols(t);
                e && (n = n.filter(function (e) {
                    return Object.getOwnPropertyDescriptor(t, e).enumerable
                })), a.push.apply(a, n)
            }
            return a
        }

        function bt(t) {
            for (var e = 1; e < arguments.length; e++) {
                var a = null != arguments[e] ? arguments[e] : {};
                e % 2 ? mt(a, !0).forEach(function (e) {
                    Object(i["a"])(t, e, a[e])
                }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(t, Object.getOwnPropertyDescriptors(a)) : mt(a).forEach(function (e) {
                    Object.defineProperty(t, e, Object.getOwnPropertyDescriptor(a, e))
                })
            }
            return t
        }

        var _t = {
            name: "Dashboard", components: {adminDashboard: et, editorDashboard: vt}, data: function () {
                return {currentRole: "adminDashboard"}
            }, computed: bt({}, Object(r["b"])(["roles"])), created: function () {
            }
        }, Ct = _t, gt = Object(d["a"])(Ct, n, s, !1, null, null, null);
        e["default"] = gt.exports
    }, ab27: function (t, e, a) {
        "use strict";
        var n = a("5682"), s = a.n(n);
        s.a
    }, b154: function (t, e, a) {
        "use strict";
        var n = a("580a"), s = a.n(n);
        s.a
    }, b63f: function (t, e, a) {
    }, efff: function (t, e, a) {
        "use strict";
        var n = a("0fec"), s = a.n(n);
        s.a
    }, f1f8: function (t, e, a) {
        "use strict";
        var n = a("b63f"), s = a.n(n);
        s.a
    }, f86f: function (t, e, a) {
        "use strict";
        var n = a("1ac3"), s = a.n(n);
        s.a
    }
}]);