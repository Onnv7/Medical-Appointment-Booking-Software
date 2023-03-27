'use strict';
/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.createTable('timelines', {
      id: {
            allowNull: false,
            autoIncrement: true,
            primaryKey: true,
            type: Sequelize.INTEGER
        },
        start: 
        {
            allowNull: false,
            type: Sequelize.DATE
        },
        end: 
        {
            type: Sequelize.DATE
        },
        createdAt: 
        {
            allowNull: false,
            type: Sequelize.DATE
        },
        updatedAt: 
        {
            allowNull: false,
            type: Sequelize.DATE
        }
    });
  },
  async down(queryInterface, Sequelize) {
    await queryInterface.dropTable('timelines');
  }
};