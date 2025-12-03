const couldNotFind = "Could Not Find Method";

var myObject = {
  create: function (prototypeList) {
    var t = {};

    t.prototypeList = prototypeList;
    if (t.prototypeList === null || t.prototypeList === undefined)
      t.prototypeList = [];

    t.create = this.create;

    t.call = function (funcName, parameters, receiver) {
      if (receiver === undefined) receiver = this;

      if (typeof this[funcName] === "function")
        return this[funcName].apply(receiver, parameters);

      for (const prototype of this.prototypeList) {
        let result = prototype.call(funcName, parameters, receiver);

        if (result === couldNotFind) continue;

        return result;
      }

      return couldNotFind;
    };

    t.addPrototype = function (prototype) {
      if (prototype === this || prototype.hasPrototype(this))
        throw Error("will result in circular prototypes");
      this.prototypeList.push(prototype);
    };

    t.hasPrototype = function (prototype) {
      for (const p of this.prototypeList) {
        if (p === prototype) return true;
        if (p.hasPrototype(prototype)) return true;
      }
      return false;
    };

    return t;
  },
};
