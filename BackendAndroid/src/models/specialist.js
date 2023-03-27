'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Specialist extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
        Specialist.belongsTo(models.Doctor, {foreignKey: 'specialist_id'})
    }
  }
  Specialist.init({
    name: DataTypes.STRING,
  }, {
    sequelize,
    modelName: 'Specialist',
  });
  return Specialist;
};