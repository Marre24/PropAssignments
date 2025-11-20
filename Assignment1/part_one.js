const couldNotFind = "Could Not Find Method";

var myObject = {
  create: function (prototypeList) {
    var t = {};
    t.prototypeList = prototypeList;
    if (t.prototypeList === null || t.prototypeList === undefined)
      t.prototypeList = [];
    t.create = this.create;
    t.call = this.call;
    t.addPrototype = this.addPrototype;
    t.hasPrototype = this.hasPrototype;
    return t;
  },

  addPrototype: function (prototype) {
    if (prototype.hasPrototype(this))
      throw Error("will result in circular prototypes");
    this.prototypeList.push(prototype);
  },

  hasPrototype: function (prototype) {
    for (const p of this.prototypeList) {
      if (p.hasPrototype(prototype)) return true;
      if (p === prototype) return true;
    }
    return false;
  },

  call: function (funcName, parameters) {
    let visited = [];

    if (arguments.length >= 3) visited = arguments[2];

    if (visited.includes(this)) return couldNotFind;

    visited.push(this);

    if (typeof this[funcName] === "function")
      return this[funcName](...parameters);

    for (const prototype of this.prototypeList) {
      let result = prototype.call(funcName, parameters, visited.slice());

      if (result === couldNotFind) continue;

      return result;
    }

    return couldNotFind;
  },
};
