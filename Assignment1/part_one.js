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
    t.checkForPrototypeFunctions = this.checkForPrototypeFunctions;

    return t;
  },

  addPrototype: function (prototype) {
    if (prototype.hasPrototype(this))
      throw Error("will result in circular prototypes");
    this.prototypeList.push(prototype);
  },

  hasPrototype: function (prototype) {
    for (const p of this.prototypeList) {
      if (p === prototype) return true;
      if (p.hasPrototype(prototype)) return true;
    }
    return false;
  },

  call: function (funcName, parameters, visited = []) {
    if (typeof this[funcName] === "function")
      return this[funcName](...parameters);

    if (visited.includes(this)) return couldNotFind;
    visited.push(this);

    for (const prototype of this.prototypeList) {
      let result = prototype.call(funcName, parameters, visited.slice());

      if (result === couldNotFind) continue;

      return result;
    }

    return couldNotFind;
  },
};
