import express from 'express'
import { createServer } from 'http'
import { Server } from 'socket.io'
import { config } from 'dotenv'

config()

const whitelist = JSON.parse(process.env.whitelist)

const port = process.env.PORT || 7777

const app = express()

const httpServer = createServer(app)

const io = new Server(httpServer, {
  cors: {
    origin(origin, callback) {
      whitelist.indexOf(origin) !== -1
        ? callback(null, true)
        : callback(new Error('Not allowed by CORS'))
    },
  },
})

io.on('connection', socket => {
  if (process.env.NODE_ENV === 'development') {
    console.log('connection::', socket.id)
  }

  socket.on('join', args => {
    const { company_id } = args

    socket.join((socket['room_name'] = company_id))
  })

  socket.on('enqueue', args => {
    socket.to(socket['room_name']).emit('dequeue', args)
  })

  socket.on('disconnect', () => {
    if (process.env.NODE_ENV === 'development') {
      console.log('disconnected from ', socket.id)
    }
  })
})

app.use(express.static('client-test'))

httpServer.listen(port)

httpServer.on('listening', () => {
  console.log(`SOCKET IO SEVER LISTENING ON http://localhost:${port}`)
})
