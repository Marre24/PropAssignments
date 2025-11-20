const couldNotFind = "Could Not Find Method";

createClass = function (className, superClassList) {
  if (superClassList === null || superClassList === undefined)
    superClassList = [];
  const c = {
    className: className,
    superClassList: superClassList,

    new() {
      var t = {};
      t.__class__ = this;
      t.call = this.call;
      t.checkForClassMethods = this.checkForClassMethods;
      return t;
    },

    addSuperClass: function (superClass) {
      if (superClass.hasSuperClass(this))
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

    call(funcName, parameters) {
      if (typeof this[funcName] === "function")
        return this[funcName](...parameters);

      return this.checkForClassMethods(this.__class__, funcName, parameters);
    },

    checkForClassMethods(currentClass, funcName, parameters, visited = []) {
      if (visited.includes(currentClass)) return couldNotFind;
      visited.push(currentClass);

      if (typeof currentClass[funcName] === "function")
        return currentClass[funcName](...parameters);

      for (const superClass of currentClass.superClassList) {
        let result = this.checkForClassMethods(
          superClass,
          funcName,
          parameters,
          visited.slice()
        );

        if (result === couldNotFind) continue;

        return result;
      }

      return couldNotFind;
    },
  };

  return c;
};
