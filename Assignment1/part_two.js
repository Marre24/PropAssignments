const couldNotFind = "Could Not Find Method";

createClass = function (className, superClassList) {
  if (superClassList === null || superClassList === undefined)
    superClassList = [];

  return {
    className: className,
    superClassList: superClassList,

    new: function () {
      var t = {};

      t.__class__ = this;

      t.call = function (funcName, parameters) {
        if (typeof this[funcName] === "function")
          return this[funcName].apply(this, parameters);

        return this.__class__.checkForClassMethods(
          this.__class__,
          funcName,
          parameters,
          this
        );
      };

      return t;
    },

    addSuperClass: function (superClass) {
      if (superClass === this || superClass.hasSuperClass(this))
        throw Error("will result in circular class inheritance");
      this.superClassList.push(superClass);
    },

    hasSuperClass: function (superClass) {
      for (const c of this.superClassList) {
        if (c === superClass) return true;
        if (c.hasSuperClass(superClass)) return true;
      }
      return false;
    },

    checkForClassMethods: function (
      currentClass,
      funcName,
      parameters,
      receiver
    ) {
      if (typeof currentClass[funcName] === "function")
        return currentClass[funcName].apply(receiver, parameters);

      for (const superClass of currentClass.superClassList) {
        let result = this.checkForClassMethods(
          superClass,
          funcName,
          parameters,
          receiver
        );

        if (result === couldNotFind) continue;

        return result;
      }

      return couldNotFind;
    },
  };
};
