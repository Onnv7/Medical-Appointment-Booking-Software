'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Booking extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Booking.belongsTo(models.Doctor, {foreignKey: 'doctor_id', targetKey: 'id'})
      Booking.belongsTo(models.Patient, {foreignKey: 'user_id', targetKey: 'id'})
    }
  }
  Booking.init({
    user_id: DataTypes.INTEGER,
    doctor_id: DataTypes.INTEGER,
    reminder: DataTypes.STRING,
    status: DataTypes.STRING,
    advice: DataTypes.STRING,
    review: DataTypes.STRING,
    start: DataTypes.INTEGER
  }, {
    sequelize,
    modelName: 'Booking',
  });
  return Booking;
};