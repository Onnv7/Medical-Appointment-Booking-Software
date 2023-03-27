'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Timeline extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
       Timeline.hasMany(models.Schedule, {foreignKey: 'timeline_id'})
    }
  }
  Timeline.init({
    start: DataTypes.DATE,
    end: DataTypes.DATE
  }, {
    sequelize,
    modelName: 'Timeline',
  });
  return Timeline;
};