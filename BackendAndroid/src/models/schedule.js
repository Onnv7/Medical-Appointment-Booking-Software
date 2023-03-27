'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Schedule extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Schedule.belongsTo(models.Timeline, {foreignKey: 'timeline_id', targetKey: 'id'})
      Schedule.belongsTo(models.Doctor, {foreignKey: 'doctor_id', targetKey: 'id'})
    }
  }
  Schedule.init({
    date: DataTypes.DATE,
    timeline_id: DataTypes.INTEGER,
    doctor_id: DataTypes.INTEGER    
  }, {
    sequelize,
    modelName: 'Schedule',
  });
  return Schedule;
};